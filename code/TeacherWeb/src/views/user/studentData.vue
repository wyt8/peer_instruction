<template>
  <div class="student-statistics">
    <!-- 查询表单 -->
    <el-form :inline="true" :model="searchForm" class="search-form">
      <el-form-item label="课程">
        <el-input v-model="searchForm.courseId" placeholder="请输入课程ID"></el-input>
      </el-form-item>
      <el-form-item label="学号">
        <el-input v-model="searchForm.studentId" placeholder="请输入学号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="fetchData">查询</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 考勤记录表格 -->
    <h3>考勤记录</h3>
    <el-table :data="attendanceData" style="width: 100%" v-if="attendanceData.length > 0">
      <el-table-column prop="date" label="日期" sortable></el-table-column>
      <el-table-column prop="status" label="出勤状态" sortable>
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? '出勤' : '缺勤' }}
          </el-tag>
        </template>
      </el-table-column>
    </el-table>
    <el-empty v-else description="暂无考勤记录"></el-empty>
    <el-pagination
      v-if="attendanceData.length > 0"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.currentPage"
      :page-size="pagination.pageSize"
      layout="total, sizes, prev, pager, next"
      :total="pagination.total"
    ></el-pagination>

    <!-- 测试记录表格 -->
    <h3>测试记录</h3>
    <el-table :data="quizData" style="width: 100%" v-if="quizData.length > 0">
      <el-table-column prop="quiz_id" label="测验ID" sortable></el-table-column>
      <el-table-column prop="quiz_name" label="测验名称" sortable></el-table-column>
      <el-table-column prop="first_right_num" label="第一次正确的题目数" sortable></el-table-column>
      <el-table-column prop="second_right_num" label="第二次正确数" sortable></el-table-column>
      <el-table-column prop="exercise_num" label="总练习数" sortable></el-table-column>
    </el-table>
    <el-empty v-else description="暂无测试记录"></el-empty>
    <el-pagination
      v-if="quizData.length > 0"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.currentPage"
      :page-size="pagination.pageSize"
      layout="total, sizes, prev, pager, next"
      :total="pagination.total"
    ></el-pagination>
  </div>
</template>

<script>
import { ref, reactive } from "vue";
import { fetchStudentStatistics } from "../../api/admin";

export default {
  name: "StudentStatistics",
  setup() {
    const searchForm = reactive({
      courseId: "",
      studentId: "",
    });

    const attendanceData = ref([]);
    const quizData = ref([]);
    const pagination = reactive({
      currentPage: 1,
      pageSize: 5,
      total: 0,
    });

    // 查询功能：调用 fetchStudentStatistics 并更新数据
    const fetchData = async () => {
  try {
    const response = await fetchStudentStatistics(searchForm.courseId, searchForm.studentId);
    console.log("API 返回数据：", response);

    if (response ) {
      attendanceData.value = response.data.records || []; // 确保 records 存在且是数组
      quizData.value = response.data.quizzes || []; // 确保 quizzes 存在且是数组
      pagination.total = attendanceData.value.length + quizData.value.length;
      
    } else {
      console.warn("未找到有效数据");
      attendanceData.value = [];
      quizData.value = [];
    }
  } catch (error) {
    console.error("获取学生统计数据失败:", error);
  }
};

    // 重置表单
    const resetForm = () => {
      searchForm.courseId = "";
      searchForm.studentId = "";
      attendanceData.value = [];
      quizData.value = [];
    };

    // 分页控制
    const handleSizeChange = (val) => {
      pagination.pageSize = val;
    };
    const handleCurrentChange = (val) => {
      pagination.currentPage = val;
    };

    return {
      searchForm,
      attendanceData,
      quizData,
      pagination,
      fetchData,
      resetForm,
      handleSizeChange,
      handleCurrentChange,
    };
  },
};
</script>

<style scoped>
.student-statistics {
  padding: 20px;
}

.search-form {
  margin-bottom: 20px;
}
</style> 