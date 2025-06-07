<template>
    <div class="course-info-container">
        <div class="grid-container">
            <div class="grid-item-center left-top">
                <el-card>
                    <template #header>
                        <div class="card-header">
                            <span>课堂信息</span>
                        </div>
                    </template>
                    <div style="height: 28vh;">
                        <el-row>
                            <el-form :inline="true" :model="searchForm" class="search-form">
                                <el-form-item label="课堂ID">
                                    <el-input v-model="searchForm.classId" placeholder="请输入课堂ID"></el-input>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="primary" @click="onFetchClassData">查询</el-button>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="danger" @click="onEndClass">结束课堂</el-button>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="success" @click="onReviewClass">课堂回顾</el-button>
                                </el-form-item>
                            </el-form>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <div class="course-details">
                                    <div class="detail-item">
                                        <label for="class-name">课程名称:</label>
                                        <span>{{ form.class_name }}</span>
                                    </div>
                                    <div class="detail-item">
                                        <label for="teacher">授课老师:</label>
                                        <span>{{ form.teacher_name }}</span>
                                    </div>
                                    <div class="detail-item">
                                        <label for="class-time">上课时间:</label>
                                        <span>{{ form.start_time?.split(' ')[1] }} - {{ form.end_time?.split(' ')[1]
                                            }}</span>
                                    </div>
                                    <div class="detail-item">
                                        <label for="status">课堂状态:</label>
                                        <el-tag v-if="form.status" :type="getStatusType(form.status)">
                                            {{ getStatusText(form.status) }}
                                        </el-tag>
                                    </div>
                                    <div class="detail-item">
                                        <label for="test_countdown_str">课堂测试倒计时:</label>
                                        <span>{{ form.test_countdown_str }}</span>
                                    </div>
                                </div>
                            </el-col>
                            <el-col :span="12">
                                <div class="course-details">
                                    <div class="detail-item">
                                        <label for="course-name">实时签到人数:</label>
                                        <span>{{ form.sign_num }}</span>
                                    </div>
                                    <div class="detail-item">
                                        <label for="teacher">正在考试人数:</label>
                                        <span>{{ form.examing_num }}</span>
                                    </div>
                                    <div class="detail-item">
                                        <label for="teacher">已完成考试人数:</label>
                                        <span>{{ form.finished_num }}</span>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                    </div>

                </el-card>
            </div>
            <div class="grid-item-center right-top">
                <el-card style="max-width: 250px" height="28vh">
                    <template #header>签到二维码</template>
                    <qrcode-vue :value="qrcodeValue" :size="size" level="H"></qrcode-vue>
                </el-card>
            </div>
            <div class="grid-item-center bottom_left">
                <el-card>
                    <template #header>
                        <div class="card-header">
                            <span>题目列表</span>
                        </div>
                    </template>
                    <el-table :data="form.exercise_list" style="width: 100%" height="33vh">
                        <el-table-column prop="exercise_id" label="题目id" sortable>
                        </el-table-column>
                        <el-table-column prop="exercise_name" label="题目名称" sortable></el-table-column>
                        <el-table-column prop="test1_status" label="测试1状态" sortable>
                            <template #default="scope">
                                <el-tag v-if="scope.row.test1_status" :type="getTestType(scope.row.test1_status)">
                                    {{ getTestText(scope.row.test1_status) }}
                                </el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column prop="test2_status" label="测试2状态" sortable>
                            <template #default="scope">
                                <el-tag v-if="scope.row.test2_status" :type="getTestType(scope.row.test2_status)">
                                    {{ getTestText(scope.row.test2_status) }}
                                </el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" min-width="200">
                            <template #default="scope">
                                <el-button type="primary" size="mini" :disabled="scope.row.test1_status >= 2"
                                    @click="onPrePublishTest(scope.row, 1)">发布测试1</el-button>
                                <el-button type="success" size="mini" :disabled="scope.row.test2_status >= 2"
                                    @click="onPrePublishTest(scope.row, 2)">发布测试2</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </div>
            <div class="grid-item-center bottom_right">
                <el-card>
                    <template #header>
                        <div class="card-header">
                            <span>学生列表</span>
                        </div>
                    </template>
                    <el-table :data="studentData" style="width: 100%;" height="33vh">
                        <el-table-column prop="student_id" label="学生ID" sortable></el-table-column>
                        <el-table-column prop="student_name" label="学生名称" sortable></el-table-column>
                        <el-table-column prop="is_signed" label="签到状态" sortable>
                            <template #default="scope">
                                <el-tag :type="scope.row.is_signed ? 'success' : 'danger'">
                                    {{ scope.row.is_signed ? '已签到' : '未签到' }}
                                </el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column prop="exercise_num" label="答题数量" sortable></el-table-column>
                    </el-table>
                </el-card>
            </div>

        </div>
        <el-dialog v-model="dialogReview" title="课程回顾" width="600px">
            <el-row :gutter="20">
                <el-col :span="8">
                    <strong>课程 ID:</strong> {{ review.course_id }}
                </el-col>
                <el-col :span="8">
                    <strong>班级 ID:</strong> {{ review.class_id }}
                </el-col>
                <el-col :span="8">
                    <strong>班级名称:</strong> {{ review.class_name }}
                </el-col>
            </el-row>
            <el-row :gutter="20" style="margin-top: 10px;">
                <el-col :span="8">
                    <strong>开始时间:</strong> {{ review.start_time }}
                </el-col>
                <el-col :span="8">
                    <strong>结束时间:</strong> {{ review.end_time }}
                </el-col>
                <el-col :span="8">
                    <strong>状态:</strong> {{ review.status }}
                </el-col>
            </el-row>
            <el-row style="margin-top: 10px;">
                <el-col :span="24">
                    <strong>练习列表:</strong>
                    <ul>
                        <li v-for="(exercise, index) in review.exercise_list" :key="index">
                            <strong>练习 ID:</strong> {{ exercise.exercise_id }}<br>
                            <strong>Test1 完成率:</strong> {{ exercise.test1_rate }}%<br>
                            <strong>Test2 完成率:</strong> {{ exercise.test2_rate }}%
                        </li>
                    </ul>
                </el-col>
            </el-row>
            <el-row style="margin-top: 10px;">
                <el-col :span="24">
                    <strong>总完成率:</strong>
                    <ul>
                        <li><strong>Test1 总完成率:</strong> {{ review.rate_total.test1_rate }}%</li>
                        <li><strong>Test2 总完成率:</strong> {{ review.rate_total.test2_rate }}%</li>
                    </ul>
                </el-col>
            </el-row>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogReview = false">取 消</el-button>
                    <el-button type="primary" @click="dialogReview = false">确 定</el-button>
                </span>
            </template>
        </el-dialog>
        <el-dialog v-model="dialogExercise" title="发布测试" width="800">
            <el-form ref="form1" :model="formExercise" label-width="120px">
                <el-form-item label="测试时间(分钟)">
                    <el-input v-model="formExercise.test_time"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogExercise = false">取 消</el-button>
                <el-button type="primary" @click="onPublishTest">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import QrcodeVue from 'qrcode.vue';
import { fetchClassDetail, fetchStudentList, publishExercise, fetchClassReview, endClass } from '../../api/class';
import { ElNotification } from 'element-plus';
const searchForm = ref({
    classId: 1,
});

const size = ref(200); // 二维码的尺寸
const route = useRoute();
const studentData = ref([]);
const dialogReview = ref(false);
const dialogExercise = ref(false);
interface Review {
    course_id: number;
    class_id: number;
    class_name: string;
    start_time: string;
    end_time: string;
    status: number;
    exercise_list: Array<any>;
    rate_total: {
        test1_rate: number;
        test2_rate: number;
    }
}

const formExercise = ref({
    test_time: 10,
    row: {} as any,
    test_num: 1,
} as any);
const review = ref<Review>({} as Review);
interface ClassForm {
    course_id: number;
    class_id: number;
    class_name?: string;
    teacher_name?: string;
    start_time?: string;
    end_time?: string;
    status?: number;
    sign_num?: number;
    examing_num?: number;
    finished_num?: number;
    test_countdown: number;
    test_countdown_str?: string;
    exercise_list?: Array<any>;
}

const form = ref<ClassForm>({} as ClassForm);

const qrcodeValue = ref("");


onMounted(async () => {
    console.log(route);
    if (route.query.classId || searchForm.value.classId) {
        searchForm.value.classId = Number(route.query.classId) || searchForm.value.classId;
        onFetchClassData();
    }
});

// 结束课堂
const onEndClass = async () => {
    console.log("结束课堂");
    let res = await endClass(form.value.course_id, form.value.class_id);
    console.log(res);
    form.value.status = 6;
    ElNotification({
        title: '结束课堂成功',
        type: 'success',
    });
}

const onFetchClassData = async () => {
    if (!searchForm.value.classId) {
        return;
    }
    let classData = await fetchClassDetail(searchForm.value.classId);
    let studentList = await fetchStudentList(searchForm.value.classId);
    console.log(classData);
    console.log(studentList);
    form.value = classData.data.classes;
    studentData.value = studentList.data.students;

    generateQRCode(form.value.course_id, form.value.class_id);
};

const generateQRCode = (course_id: number, class_id: number) => {
    // 生成二维码的逻辑
    qrcodeValue.value = `https://apifoxmock.com/m1/5331430-5002107-default/students/{student_id}/courses/${course_id}/classes/${class_id}`;
};

const getStatusText = (status: number): string => {
    const statusMap: Record<string, string> = {
        "1": '刚创建',
        "2": '已添加题目',
        "3": '已发布课堂',
        "4": '已发布签到',
        "5": '已发放题目',
        "6": '已结束'
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

const getTestType = (status: number): StatusType => {
    const typeMap: Record<string, StatusType> = {
        "1": 'info',
        "2": 'success',
        "3": 'danger'
    };
    return typeMap[status.toString()] || 'info';
}

const getTestText = (status: number): string => {
    const textMap: Record<string, string> = {
        "1": '未开始',
        "2": '进行中',
        "3": '已结束'
    };
    return textMap[status.toString()] || '未知';
}

const onPrePublishTest = (row: any, test_num: number) => {
    formExercise.value.row = row;
    formExercise.value.test_num = test_num;
    if (test_num == 2 && row.test1_status == 2) {
        ElNotification({
            title: '请先完成测试1',
            type: 'warning',
        });
        return;
    }
    dialogExercise.value = true;
}

const formatSeconds = (seconds: number) => {
    var minutes = Math.floor(seconds / 60);
    var remainingSeconds = seconds % 60;
    return minutes + "分钟" + remainingSeconds + "秒";
}

const onPublishTest = async () => {
    let res = await publishExercise(form.value.class_id,  formExercise.value.row.exercise_id, formExercise.value.test_num);
    formExercise.value.row[`test${formExercise.value.test_num}_status`] = 1;
    form.value.test_countdown = formExercise.value.test_time * 60;
    console.log(form.value.test_countdown);
    setInterval(() => {
        form.value.test_countdown_str = formatSeconds(form.value.test_countdown-- || 0);
    }, 1000);
    dialogExercise.value = false;
    ElNotification({
        title: '发布测试成功',
        type: 'success',
    });
}

const onReviewClass = async () => {
    console.log('课程回顾');
    let res = await fetchClassReview(form.value.course_id, form.value.class_id);
    review.value = res.data.review;
    dialogReview.value = true;
}
</script>

<style scoped>
.course-info-container {
    height: 90vh;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 1rem;
}

.grid-container {
    display: grid;
    grid-template-columns: 2fr 1fr;
    /* 左上三分之二，右上三分之一 */
    grid-template-rows: 1fr 2fr;
    /* 上下两行，上行一份额，下行两份额 */
    gap: 1rem;
    width: 90%;
    height: 90%;
}

.grid-item {
    padding: 1rem;
    box-sizing: border-box;
}

.left-top {
    grid-column: 1 / 2;
    /* 占据第一到第二条垂直线，即两个份额 */
    grid-row: 1 / 2;
}

.right-top {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    grid-column: 2 / 3;
    /* 占据第二到第三条垂直线，即一个份额 */
    grid-row: 1 / 2;
}

.bottom_left {
    grid-column: 1 / 2;
    grid-row: 2 / 3;
}

.bottom_right {
    grid-column: 2 / 3;
    grid-row: 2 / 3;
}

.course-details {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
    max-height: 80%;
    overflow-y: auto;
    width: 100%;
}

.detail-item {
    display: flex;
    justify-content: space-between;
    width: 100%;
    margin-bottom: 0.5rem;
}

.detail-item label {
    flex-basis: 40%;
    text-align: right;
    margin-right: 1rem;
}

.detail-item span {
    flex-basis: 60%;
    text-align: left;
}

.chat-area {
    display: flex;
    flex-direction: column;
    height: 100%;
}

.chat-messages {
    flex-grow: 1;
    padding: 1rem;
    border: 1px solid #ccc;
    background-color: #f9f9f9;
}

input[type="text"] {
    width: 100%;
    padding: 0.5rem;
    margin-bottom: 0.5rem;
    border: 1px solid #ccc;
    border-radius: 4px;
}
</style>