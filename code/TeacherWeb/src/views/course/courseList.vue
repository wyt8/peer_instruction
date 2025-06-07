<template>
  <div class="teacher-courses">
    <!-- 查询表单 -->
    <el-form :inline="true" :model="searchForm" class="search-form">
      <el-form-item label="教师ID">
        <el-input v-model="searchForm.teacherId" placeholder="请输入教师ID"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="fetchData">查询</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 课程信息表格 -->
    <h3>课程信息</h3>
    <el-table :data="courseData" style="width: 100%" v-if="courseData.length > 0">
      <el-table-column prop="course_id" label="课程ID" sortable></el-table-column>
      <el-table-column prop="course_name" label="课程名称" sortable></el-table-column>
      <el-table-column prop="course_image" label="课程图片">
        <template #default="scope">
          <img :src="scope.row.course_image_url" alt="课程图片" style="width: 80px; height: 80px;" />
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button type="primary" size="mini" @click="onPreClassCreate(scope.row.course_id)">创建课堂</el-button>
          <el-button type="success" size="mini" @click= "()=>onSendNotice(scope.row.course_id)">发送公告</el-button>
          <el-button type="success" size="mini" @click="goToDiscussion(scope.row.course_id)">讨论区</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-empty v-else description="暂无课程信息"></el-empty>
    <el-dialog v-model="dialogVisible" title="创建课堂" width="800">
      <el-form ref="form" :model="formCreateClass" label-width="120px" label-position="right">
        <el-form-item label="课程名称">
          <el-input v-model="formCreateClass.class_name"></el-input>
        </el-form-item>
        <el-form-item label="课程开始时间" label-position="right">
          <el-time-picker v-model="formCreateClass.start_time" placeholder="" />
        </el-form-item>
        <el-form-item label="课程结束时间" label-position="right">
          <el-time-picker v-model="formCreateClass.end_time" placeholder="" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogCreateClass = false">取 消</el-button>
        <el-button type="primary" @click="onClassCreate">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { ref, reactive, onMounted } from "vue";
import { fetchTeacherCourses,sendCourseNotice } from "../../api/admin";
import { ElMessageBox,ElNotification, ElDialog } from 'element-plus';
import { useRouter } from 'vue-router';
import { createClass } from "../../api/class";
import { useUserStore } from "../../store/user";

const userStore=useUserStore();

export default {
  name: "TeacherCourses",
  setup() {
    const searchForm = reactive({
      teacherId: "",
    });

    const courseData = ref([]); // 存储课程信息
    const router = useRouter();
    const dialogVisible = ref(false);
    const formCreateClass = reactive({
      course_id: "",
      class_name: "",
      start_time: "",
      end_time: "",
    });
    const dialogCreateClass = ref(false);
    // 默认加载数据
  onMounted(() => {
    // 从用户 Store 获取 user_id
    if (userStore.user && userStore.user.user_id) {
      searchForm.teacherId = userStore.user.user_id; // 填充输入框
      fetchData(); // 自动触发查询
    } else {
      console.warn("未找到用户ID，无法加载数据");
    }
  });
    // 查询功能
    const fetchData = async () => {
      try {
        if (!searchForm.teacherId) {
          console.warn("教师ID不能为空！");
          return;
        }
        const response = await fetchTeacherCourses(searchForm.teacherId);
        console.log("接口返回数据：", response);

        if (response) {
          // 解析返回的数据
          courseData.value = response.data.courses || [];
        } else {
          console.warn("未找到有效数据:", response.msg);
          courseData.value = [];
        }
      } catch (error) {
        console.error("获取课程数据失败：", error);
      }
    };


    // 预创建课堂
    const onPreClassCreate = async (courseId) => {
      formCreateClass.course_id = courseId;
      dialogCreateClass.value = true;
      dialogVisible.value = true;
      console.log("预创建课堂：", dialogCreateClass);
    };


    // 创建课堂
    const onClassCreate = async () => {
      console.log("创建课堂：", formCreateClass);
      await createClass(formCreateClass.course_id, formCreateClass);
      ElNotification({
        title: '创建课堂成功',
        type: 'success',
      });

      setTimeout(() => {
        router.push(`/index/classList?courseId=${formCreateClass.course_id}`);
      }, 1000);
    };


    // 重置表单
    const resetForm = () => {
      searchForm.teacherId = "";
      courseData.value = [];
    };

    // 发送公告
const onSendNotice = async (courseId) => {
  try {
    // 弹出输入框，获取公告内容
    const { value: noticeContent } = await ElMessageBox.prompt(
      "请输入公告内容",
      "发送公告",
      {
        confirmButtonText: "发送",
        cancelButtonText: "取消",
      }
    );

    if (noticeContent) {
      // 调用封装好的接口
      await sendCourseNotice(courseId, noticeContent);
    }
  } catch (error) {
    console.error("发送公告失败：", error);
    ElNotification({
      title: "发送失败",
      message: "公告发送过程中出现错误",
      type: "error",
    });
  }
};

    //进入讨论区
    const goToDiscussion = (courseId) => {
      router.push({
        path: `/index/${courseId}/discussions`,
      });
    };

    return {
      searchForm,
      goToDiscussion,
      onSendNotice,
      courseData,
      fetchData,
      resetForm,
      dialogCreateClass,
      formCreateClass,
      onPreClassCreate,
      onClassCreate,
      router,
      dialogVisible,

    };
  },
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