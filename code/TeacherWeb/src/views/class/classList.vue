<template>
    <div style="padding: 20px;">
        <el-form :inline="true" :model="searchForm" class="search-form">
            <el-form-item label="课程ID">
                <el-input v-model="searchForm.courseId" placeholder="请输入课程ID"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="fetchData">查询</el-button>
                <el-button @click="resetForm">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 课程信息表格 -->
        <h3>课堂信息</h3>
        <el-table :data="courseData" style="width: 100%" v-if="courseData.length > 0">
            <!-- <el-table-column prop="course_id" label="课程ID" sortable></el-table-column> -->
            <!-- <el-table-column prop="class_id" label="课堂ID" sortable></el-table-column> -->
            <el-table-column prop="class_name" label="课堂名称" sortable></el-table-column>
            <el-table-column prop="start_time" label="开始时间" sortable></el-table-column>
            <el-table-column prop="end_time" label="结束时间" sortable></el-table-column>
            <el-table-column prop="status" label="状态" sortable>
                <template #default="scope">
                    <el-tag :type="getStatusType(scope.row.status)">
                        {{ getStatusText(scope.row.status) }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" min-width="250">
                <template #default="scope">
                    <el-button size="mini" @click="onClassDetail(scope.row.class_id)">进入课堂</el-button>
                    <el-button v-if="scope.row.status < 2" type="primary" size="mini"
                        @click="handleAddExercise(scope.row.class_id)">添加题目</el-button>
                    <el-button v-if="scope.row.status < 3" type="primary" size="mini"
                        @click="onPublishClass(scope.row.class_id)">发布课堂</el-button>
                    <el-button v-if="scope.row.status < 4" type="success" size="mini"
                        @click="onPublishSign(scope.row.class_id)">发布签到</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-empty v-else description="暂无课堂信息"></el-empty>

        <el-dialog v-model="dialogVisible" title="题目列表" width="800">
            <el-table 
                :data="exerciseData" 
                style="width: 100%" 
                v-if="exerciseData.length > 0"
                @selection-change="handleSelectionChange" 
                ref="multipleTable">
                <el-table-column type="selection" width="55" />
                <el-table-column prop="question_id" label="题目ID"></el-table-column>
                <el-table-column prop="name" label="题目名称"></el-table-column>
            </el-table>
            <el-empty v-else description="暂无题目"></el-empty>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="onAddExercise">提交</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from "vue";
import { fetchClassList, addExercise, publishClass, publishSign, fetchExerciseList } from "../../api/class";
import { ElNotification, ElTag } from 'element-plus';
import { useRouter } from 'vue-router';

const router = useRouter();
const dialogVisible = ref(false);
const searchForm = reactive({
    courseId: 1
});
const exerciseData = ref<any[]>([]); // 明确指定类型为数组
const courseData = ref([]); // 存储课程信息
const selectedExercise = ref<any[]>([]); // 存储选中的题目
const multipleTable = ref(); // 添加表格引用

const currentClassId = ref(0); // 当前课堂ID

// 默认加载数据
onMounted(() => {
    if (router.currentRoute.value.query.courseId != null) {
        searchForm.courseId =  Number(router.currentRoute.value.query.courseId)
    }
    fetchData()
});

const getStatusText = (status: number): string => {
    const statusMap: Record<string, string> = {
        "1": '刚创建',
        "2": '已添加题目',
        "3": '已发布课堂',
        "4": '已发布签到',
        "5": '已发放题目'
    };
    return statusMap[status.toString()] || '未知';
};

type StatusType = 'success' | 'warning' | 'info' | 'primary' | 'danger';

const getStatusType = (status: number): StatusType => {
    const typeMap: Record<string, StatusType> = {
        "1": 'info',
        "2": 'success',
        "3": 'primary',
        "4": 'warning',
        "5": 'danger'
    };
    return typeMap[status.toString()] || 'info';
}

const handleSelectionChange = (val: any[]) => {
    selectedExercise.value = val;
};

// 查询功能
const fetchData = async () => {
    try {
        if (!searchForm.courseId) {
            console.warn("课程ID不能为空！");
            return;
        }
        const response = await fetchClassList(searchForm.courseId.toString());
        console.log("接口返回数据：", response);

        if (response) {
            // 解析返回的数据
            courseData.value = response.data.classes || [];
        } else {
            console.warn("未找到有效数据:", response.msg);
            courseData.value = [];
        }
    } catch (error) {
        console.error("获取课程数据失败：", error);
    }
};

// 打开添加题目弹窗
const handleAddExercise = async (classId: number) => {
    try {
        currentClassId.value = classId;
        let courseId = searchForm.courseId;
        const response = await fetchExerciseList(courseId);
        if (response && response.data) {
            exerciseData.value = response.data.questions_list || [];
        }
        dialogVisible.value = true;
    } catch (error) {
        console.error("获取题目列表失败：", error);
        ElNotification({
            title: '错误',
            message: '获取题目列表失败',
            type: 'error',
        });
    }
};

// 添加题目
const onAddExercise = async () => {
    const exerciseList = selectedExercise.value.map(item => item.question_id);
    console.log("添加题目：", currentClassId.value, exerciseList);
    await addExercise(currentClassId.value, exerciseList);
    ElNotification({
        title: '添加题目成功',
        type: 'success',
    });
    dialogVisible.value = false;
};

// 发布课堂
const onPublishClass = async (classId: number) => {
    console.log("发布课堂：", classId);
    await publishClass(classId);
    ElNotification({
        title: '发布课堂成功',
        type: 'success',
    });
};

// 发布签到
const onPublishSign = async (classId: number) => {
    console.log("发布签到：", classId);
    await publishSign(classId);
    ElNotification({
        title: '发布签到成功',
        type: 'success',
    });
};


// 进入课堂
const onClassDetail = async (classId: number) => {
    console.log("进入课堂：", classId);
    router.push(`/index/classDetail?classId=${classId}`);
};
    
// 重置表单
const resetForm = () => {
    searchForm.courseId = 1;
    courseData.value = [];
};

</script>

<style scoped>
.teacher-courses {
    padding: 20px;
}

.search-form {
    margin-bottom: 20px;
}

h3 {
    margin-bottom: 10px;
}

img {
    border: 1px solid #ddd;
    border-radius: 5px;
}
</style>