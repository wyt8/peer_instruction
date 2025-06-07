import { $post, $get,$delete } from "../utils/request.ts";
import { ElNotification } from 'element-plus';
import { useUserStore } from '../store/user';
//mock 文件
import '../mock/mock';


// 登录方法
export const $Login = async (params: { role: number; email: string; password: string }) => {
    const ret = await $post('/login', params);
    
    if (ret.code === 0) {  // code = 0 表示登录成功
        ElNotification({
            title: '通知',
            message: '登录成功',  // 显示来自服务器的成功消息
            type: 'success',
        });
        // 登录成功后，将 token 信息保存到浏览器缓存中
        sessionStorage.setItem('token', ret.token);
    } else {
        ElNotification({
            title: '通知',
            message: '请检查邮箱和密码',  // 自定义的错误提示
            type: 'error',
        });
    }

    console.log('登录返回:', ret);
    return ret;  // 返回数据以便在调用方使用
};

// 获取邮箱验证码方法
export const $emailVerify = async (email: string, role: number) => {
  const params = { email, role };
  const ret = await $post('/email_verify', params);
  console.log('验证码发送返回:', ret);

  if (ret.code === 0) {  // 假设 code = 0 表示验证码发送成功
      ElNotification({
          title: '通知',
          message: '验证码已发送，请检查您的邮箱',
          type: 'success',
      });
  } else {
      ElNotification({
          title: '通知',
          message: '验证码发送失败，请重试',
          type: 'error',
      });
  }

  return ret;  // 返回数据以便在调用方使用
};

// 注册方法
export const $register = async (verification_code: number, email: string, password: string, role: number) => {
    const params = { verification_code, email, password, role };
    const ret = await $post('/register', params);

    if (ret.code === 0) {  // code = 0 表示登录成功
      ElNotification({
          title: '通知',
          message: '注册成功',  // 显示来自服务器的成功消息
          type: 'success',
      });
      // 登录成功后，将 token 信息保存到浏览器缓存中
      sessionStorage.setItem('token', ret.token);
  } else {
      ElNotification({
          title: '通知',
          message: '请检查邮箱和验证码',  // 自定义的错误提示
          type: 'error',
      });
  }



    console.log('注册返回:', ret);
    return ret;  // 返回数据以便在调用方使用
};





//创建课程
export const $createCourse = async (teacherId: number, courseName: string, courseImageUrl: string) => {
    const params = {
        course_name: courseName,
        course_image_url: courseImageUrl
    };
    const ret = await $post(`/courses/teachers/${teacherId}`, params);

    if (ret.code === 0) {
        ElNotification({
            title: '通知',
            message: `课程创建成功！课程id为 ${ret.data.course_id}。`,
            type: 'success',
        });
    } else {
        ElNotification({
            title: '通知',
            message: ret.msg || '课程创建失败',
            type: 'error',
        });
    }

    console.log('创建课程返回:', ret);
    return ret;
};

//上传图片接口
export const $uploadImage = async (file: File): Promise<{ url: string }> => {
  const formData = new FormData();
  formData.append('img', file); // 将文件添加到 FormData 中

  try {
      const ret = await $post('/upload_img', formData, {
          headers: {
              'Content-Type': 'multipart/form-data', // 确保是 multipart 格式
          },
      });

      // 成功处理
      if (ret.status===0&& ret.data.url) {
          ElNotification({
              title: '通知',
              message: '图片上传成功！',
              type: 'success',
          });
      } else {
          // 失败处理
          ElNotification({
              title: '通知',
              message: ret.data?.msg || '图片上传失败',
              type: 'error',
          });
      }

      console.log('图片上传返回:', ret.data);
      return ret.data; // 返回服务器响应数据

  } catch (error) {
      console.error('图片上传出错:', error);

      // 捕获错误并通知用户
      ElNotification({
          title: '通知',
          message: '图片上传失败，请稍后重试',
          type: 'error',
      });

      throw error; // 抛出错误供调用方处理
  }
};




//教师加入已有的课程
export const joinCourse = async (courseId:string) => {
  const userStore = useUserStore();
  const teacherId = userStore.user.user_id;

  if (!teacherId) {
    ElNotification({
      title: '错误',
      message: '用户信息丢失，请重新登录。',
      type: 'error',
    });
    return;
  }

  const payload = {
    teacher_id: teacherId,
  };

  try {
    const response = await $post(`/courses/${courseId}/attend`, payload);

    // 成功时的通知逻辑
    if (response.code === 0) {
      ElNotification({
        title: '成功',
        message: '已成功加入课程！',
        type: 'success',
      });
    } else {
      // 错误时的通知逻辑
      ElNotification({
        title: '错误',
        message: response.msg || '加入课程失败，请重试。',
        type: 'error',
      });
    }

    console.log('加入课程返回:', response);
    return response;
  } catch (error) {
    // 捕获异常并通知
    console.error('加入课程请求出错:', error);
    ElNotification({
      title: '错误',
      message: '加入课程时发生错误，请稍后重试。',
      type: 'error',
    });
    throw error;
  }
};



  //查询学生数据
  export const fetchStudentStatistics = async (courseId: string, studentId: string) => {
    
    try {
      const response = await $get(`/courses/${courseId}/students/${studentId}/statistics`);
      if (response.code === 0) {
        ElNotification({
          title: '查询成功',
          
          type: 'success',
        });
      } else {
        ElNotification({
          title: '查询失败',
          message: response.msg ,
          type: 'error',
        });
      }
      return response;
    } catch (error) {
      console.error("获取学生统计数据失败:", error);
      throw error;
    }
  };

  //查询课程中的学生
  export const fetchCourseStudents = async (courseId: string, studentId?: string) => {
    try {
      // 构造请求 URL  QUERY参数
      const apiUrl = `/courses/students?course_id=${courseId}${studentId ? `&student_id=${studentId}` : ''}`;
      // 发起 GET 请求
      const response = await $get(apiUrl);
  
      // 检查响应状态
      if (response.code === 0) {
        ElNotification({
          title: '查询成功',
          type: 'success',
        });
      } else {
        ElNotification({
          title: '查询失败',
          message: response.msg,
          type: 'error',
        });
      }
  
      // 返回结果
      return response;
    } catch (error) {
      console.error("获取课程学生数据失败:", error);
  
      // 抛出错误以供调用方处理
      throw error;
    }
  };
  

//查询特定课程
  export const fetchCourses = async (courseId?: string, courseName?: string) => {
    // 构造查询参数对象
    const queryParams = new URLSearchParams({
      course_id: courseId,
      course_name: courseName,
    }).toString();
    
    
    try {
      const response = await $get(`/courses?${queryParams}`);
      
      if (response.code === 0) {  // 假设 code = 0 表示查询成功
        ElNotification({
          title: '查询成功',
          message: '课程信息已成功获取',
          type: 'success',
        });
      } else {
        ElNotification({
          title: '查询失败',
          message: response.msg || '未找到相关课程信息',
          type: 'error',
        });
      }
  
      console.log('查询课程返回:', response);
      return response;  // 返回查询结果
    } catch (error) {
      console.error('查询课程请求出错:', error);
      ElNotification({
        title: '错误',
        message: '查询课程时发生错误',
        type: 'error',
      });
      throw error;
    }
  };

  //将学生加入课程
  export const addStudentToCourse = async (courseId: string, studentId: string) => {
    try {
      
      const response = await $post(`/courses/${courseId}/students`, {
        student_id: studentId,
      });
  
      // 根据返回结果判断操作是否成功
      if (response.code === 0) {
        ElNotification({
          title: '添加成功',
          message: `学生 ${studentId} 已成功添加到课程 ${courseId}`,
          type: 'success',
        });
      } else {
        ElNotification({
          title: '添加失败',
          message: response.msg,
          type: 'error',
        });
      }
      return response;
    } catch (error) {
      // 处理请求过程中出现的错误
      console.error("添加学生至课程失败:", error);
     
      throw error;
    }
  };
  //将学生移除课程
  export const removeStudentFromCourse = async (courseId:string, studentId:string) => {
    try {
      const response = await $delete(`/courses/${courseId}/students/${studentId}`);
      if (response.code === 0) {
        ElNotification({
          title: '移除成功',
          message: `学生 ${studentId} 已从课程 ${courseId} 中移除`,
          type: 'success',
        });
      } else {
        ElNotification({
          title: '移除失败',
          message: response.msg,
          type: 'error',
        });
      }
      return response;
    } catch (error) {
      console.error("移除学生失败：", error);
      ElNotification({
        title: '请求失败',
        message: '网络错误或服务器异常，请稍后再试',
        type: 'error',
      });
      throw error;
    }
  };


//查询教师课程表
  export const fetchTeacherCourses = async (teacherId: string) => {
    try {
      // 调用接口
      const response = await $get(`/teachers/courses?teacher_id=${teacherId}`);
      
      // 成功的响应处理
      if (response.code === 0) {
        ElNotification({
          title: '查询成功',
          type: 'success',
        });
      } else {
        // 错误的响应处理
        ElNotification({
          title: '查询失败',
          message: response.msg,
          type: 'error',
        });
      }
  
      // 返回响应数据
      return response;
    } catch (error) {
      // 捕获异常并输出错误信息
      console.error("获取教师课程列表失败:", error);
  
      
      throw error;
    }
  };

  //教师发送课程通知
  export const sendCourseNotice = async (courseId: string, noticeContent: string) => {
    try {
      // 调用接口
      const response = await $post(`/courses/${courseId}/notices`, {
        notice: {
          content: noticeContent,
        },
      });
  
      // 成功的响应处理
      if (response.code === 0) {
        ElNotification({
          title: '发送成功',
          message: '通知已成功发送。',
          type: 'success',
        });
      } else {
        // 错误的响应处理
        ElNotification({
          title: '发送失败',
          message: response.msg || '未知错误',
          type: 'error',
        });
      }
  
      // 返回响应数据
      return response;
    } catch (error) {
      // 捕获异常并输出错误信息
      console.error("发送课程通知失败:", error);
  
      // 异常通知
      ElNotification({
        title: '发送失败',
        message: '网络错误或服务器未响应。',
        type: 'error',
      });
  
      // 抛出异常
      throw error;
    }
  };
  


  // 获取课堂列表
export const fetchClassList = async (courseId: string) => {
  const response = await $get(`/class/list?course_id=${courseId}`);
  return response;
};

//根据课程id创建课堂
  export const createCourseClass = async (courseId: string) => {
    try {
      // 调用接口
      // 临时写的接口, 需要后端写好, 后面再改
      const response = await $post(`/class/create`, {
        course_id: courseId,
      });
      
      // 成功的响应处理
      if (response.code === 0) {
        ElNotification({
          title: '查询成功',
          type: 'success',
        });
      } else {
        // 错误的响应处理
        // ElNotification({
        //   title: '查询失败',
        //   message: response.msg,
        //   type: 'error',
        // });
      }
  
      // 返回响应数据
      return response;
    } catch (error) {
      // 捕获异常并输出错误信息
      console.error("创建课堂失败:", error);
      
      throw error;
    }
  };

  
  // 添加题目
  export const addExercise = async (classId: string, exerciseList: Array<number>) => {
    const response = await $post(`/class/exercise/add`, {
      class_id: classId,
      exercise_list: exerciseList,
    });
    return response;
  };

  // 发布课堂
  export const publishClass = async (classId: string) => {
    const response = await $post(`/class/publish`, {
      class_id: classId,
    });
    return response;
  };


  // 发布签到
  export const publishSign = async (classId: string) => {
    const response = await $post(`/class/sign/publish`, {
      class_id: classId,
    });
    return response;
  };

  // 发布测试
  export const publishExercise = async (classId: string) => {
    const response = await $post(`/class/exercise/publish`, {
      class_id: classId,
    });
    return response;
  };

  // 查询课堂详情
  export const fetchClassDetail = async (classId: string) => {
    const response = await $get(`/class/detail?class_id=${classId}`);
    return response;
  };

  // 查询教师可以查看的题目列表
  export const fetchExerciseList = async (teacherId: number) => {
    const response = await $get(`/banks/?course_id=${teacherId}`);
    return response;
  };


