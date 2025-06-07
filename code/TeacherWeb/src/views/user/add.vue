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
          <!-- 添加学生按钮 -->
          <el-button type="success" @click="addStudent">添加学生至该课程</el-button>
        </el-form-item>
      </el-form>
  
      <!-- 学生信息表格 -->
      <h3>学生信息</h3>
      <el-table :data="studentData" style="width: 100%" v-if="studentData.length > 0">
        <el-table-column prop="student_id" label="学生ID" sortable></el-table-column>
        <el-table-column prop="student_name" label="学生姓名" sortable></el-table-column>
        <!-- 学生头像列 -->
      <el-table-column label="头像" width="80">
        <template #default="scope">
          <img
            :src="scope.row.student_avatar"
            alt="学生头像"
            style="width: 40px; height: 40px; border-radius: 50%; object-fit: cover;"
          />
        </template>
      </el-table-column>

        <el-table-column label="操作">
          <template #default="scope">
            <el-button
              type="danger"
              size="small"
              @click="removeStudent(scope.row.student_id)"
            >
              移出课程
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-else description="暂无学生信息"></el-empty>
    </div>
  </template>
  
  
  <script>
  import { ref, reactive } from "vue";
  import { fetchStudentStatistics, addStudentToCourse, removeStudentFromCourse,fetchCourseStudents } from "../../api/admin";
  
  export default {
    name: "StudentStatistics",
    setup() {
      const searchForm = reactive({
        courseId: "",
        studentId: "",
      });
  
      const studentData = ref([]); // 存储学生信息
  
    // 查询功能
const fetchData = async () => {
  try {
    if (!searchForm.courseId) {
      console.warn("课程ID不能为空！");
      return;
    }

    // 调用 fetchCourseStudents 接口
    const response = await fetchCourseStudents(searchForm.courseId, searchForm.studentId);
    console.log("接口返回数据：", response);

    if (response) {
    
      const students = response.data.students;

      // 如果是数组，直接赋值；如果是单个对象，将其转换为数组
      if (Array.isArray(students)) {
        studentData.value = students.map(({ student_id, student_name ,student_avatar}) => ({
          student_id,
          student_name,
          student_avatar
        }));
      } else if (students && typeof students === "object") {
        studentData.value = [
          {
            student_id: students.student_id,
            student_name: students.student_name,
            student_avatar: students.student_avatar,
          },
        ];
      } else {
        studentData.value = [];
        console.warn("数据格式异常或未找到学生数据！");
      }
    } else {
      console.warn("查询失败:", response.msg);
      studentData.value = [];
    }
  } catch (error) {
    console.error("获取学生数据失败：", error);
  }
};


      // 添加学生至课程
      const addStudent = async () => {
        try {
          if (!searchForm.courseId || !searchForm.studentId) {
            console.warn("课程ID或学号不能为空！");
            return;
          }
          const response = await addStudentToCourse(searchForm.courseId, searchForm.studentId);
          console.log("添加学生成功：", response);
          fetchData(); // 更新数据
        } catch (error) {
          console.error("添加学生失败：", error);
        }
      };
  
      // 删除学生
      const removeStudent = async (studentId) => {
        try {
          if (!searchForm.courseId || !studentId) {
            console.warn("课程ID或学生ID不能为空！");
            return;
          }
          const response = await removeStudentFromCourse(searchForm.courseId, studentId);
          console.log(`学生 ${studentId} 已从课程 ${searchForm.courseId} 中移除：`, response);
          fetchData(); //再次查询  学生是否保留
                  } catch (error) {
          console.error(`移除学生 ${studentId} 失败：`, error);
        }
      };
  
      // 重置表单
      const resetForm = () => {
        searchForm.courseId = "";
        searchForm.studentId = "";
        studentData.value = [];
      };
  
      return {
        searchForm,
        studentData,
        fetchData,
        resetForm,
        addStudent,
        removeStudent,
      };
    },
  };
  </script>
  <style scoped>
  .student-statistics {
    padding: 20px;
    padding-left: 20px; /* 增加左边距 */
    padding-right: 20px; /* 增加右边距 */
  }
  
  .search-form {
    margin-bottom: 20px;
    padding-left: 10px; /* 搜索表单间隔 */
  }
  
  h3 {
    margin-left: 10px; /* 标题与左边缘的间隔 */
  }
  
  .el-empty {
    margin-left: 20px; /* 提示组件与左边的间隔 */
  }
  </style>