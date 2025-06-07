<template>
  <div class="join-course">
    <!-- 查询表单 -->
    <el-form :inline="true" :model="searchForm" class="search-form">
      <el-form-item label="课程名称">
        <el-input v-model="searchForm.courseName" placeholder="请输入课程名称"></el-input>
      </el-form-item>
      <el-form-item label="课程 ID">
        <el-input v-model="searchForm.courseId" placeholder="请输入课程 ID"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchCourse">查询课程</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 查询结果 -->
    <el-table :data="courseResults" style="width: 100%" v-if="courseResults.length > 0">
      <el-table-column prop="course_id" label="课程 ID"></el-table-column>
      <el-table-column label="课程信息" align="center">
        <template #default="scope">
          <div class="course-info">
            <img :src="scope.row.course_image_url" alt="课程图片" class="course-image" />
            <div>
              <div class="course-name">{{ scope.row.course_name }}</div>
              <div class="teacher-name">教师：{{ scope.row.teacher.teacher_name }}</div>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="join_time" label="创建时间"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
            type="primary"
            :loading="buttonLoading[scope.row.course_id]"
            :disabled="buttonLoading[scope.row.course_id]"
            @click="handleJoinCourse(scope.row.course_id)"
          >
            加入课程
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-empty v-else description="未找到匹配的课程"></el-empty>
  </div>
</template>

<script>
import { ref, reactive } from 'vue';
import { fetchCourses, joinCourse } from '../../api/admin'; // 引入接口方法
import { ElMessage, ElLoading } from 'element-plus'; // 引入 Element Plus 的消息和加载功能

export default {
  name: 'Role',
  setup() {
    // 表单数据
    const searchForm = reactive({
      courseName: '',
      courseId: '',
    });

    // 查询结果
    const courseResults = ref([]);

    // 按钮加载状态（每个按钮独立）
    const buttonLoading = reactive({});

    // 查询课程
    const searchCourse = async () => {
      if (!searchForm.courseName && !searchForm.courseId) {
        ElMessage.warning('请输入课程名称或课程 ID 进行查询');
        return;
      }

      const loading = ElLoading.service({
        lock: true,
        text: '查询中...',
        background: 'rgba(0, 0, 0, 0.7)',
      }); // 显示全局加载动画

      try {
        const results = await fetchCourses(searchForm.courseId, searchForm.courseName);
        courseResults.value = results.data?.courses || [];
        if (courseResults.value.length === 0) {
          ElMessage.info('未找到匹配的课程');
        }
      } catch (error) {
        console.error('查询课程失败:', error);
        ElMessage.error('查询课程失败，请稍后重试');
      } finally {
        loading.close(); // 关闭全局加载动画
      }
    };

    // 加入课程
    const handleJoinCourse = async (courseId) => {
      buttonLoading[courseId] = true; // 设置按钮加载状态
      try {
        await joinCourse(courseId); // 调用 API 方法
        await searchCourse(); // 加入成功后刷新课程列表
      } catch (error) {
        console.error('加入课程失败:', error);
      } finally {
        buttonLoading[courseId] = false; // 关闭按钮加载状态
      }
    };

    // 重置表单
    const resetForm = () => {
      searchForm.courseName = '';
      searchForm.courseId = '';
      courseResults.value = [];
    };

    return {
      searchForm,
      courseResults,
      searchCourse,
      resetForm,
      handleJoinCourse,
      buttonLoading, // 暴露按钮加载状态
    };
  },
};
</script>

<style scoped>
.join-course {
  padding: 20px;
}

.search-form {
  margin-bottom: 20px;
}

.course-info {
  display: flex;
  align-items: center;
}

.course-image {
  max-width: 50px;
  max-height: 50px;
  margin-right: 10px;
  border-radius: 5px;
}

.course-name {
  font-weight: bold;
}

.teacher-name {
  font-size: 12px;
  color: #666;
}
</style>
