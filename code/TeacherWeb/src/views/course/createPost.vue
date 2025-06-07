<template>
    <div class="create-post">
      <div class="container">
        <div class="header">发布新帖子</div>
        <el-form :model="form" label-width="50px">
          <el-form-item label="标题" class="title" size="mini">
            <el-input type="textarea" v-model="form.title"></el-input>
          </el-form-item>
          <el-form-item label="内容" size="midium">
            <el-input
              type="textarea"
              v-model="form.content"
              class="form-content"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="onSubmit(form.title, form.value, form.content)"
              >发布</el-button
            >
            <el-button @click="cancelPost" class="cancel">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </template>
  
<script setup>
import { ref, onMounted , getCurrentInstance,inject} from 'vue';
import { addPosts } from '../../api/post';
import { useRouter, useRoute } from 'vue-router';
import { useUserStore } from "../../store/user";
  
  const form = ref({
    title: '',
    content: ''
  });
  
  const router = useRouter();
  const route = useRoute();
  const userStore = useUserStore();
  
  const courseId = ref( 0);
  const instance = getCurrentInstance();
// 获取刷新帖子方法
  const refreshDiscussions = inject('refreshDiscussions');
  const cancelPost = () => {
    router.go(-1);
  };
  
  const onSubmit = () => {
    const posterId = userStore.user.user_id.toString; // 假设 user_id 是响应式的
    const posterRole = '2';
    
    addPosts(courseId.value, form.value.title, form.value.content, posterId, posterRole).then(res => {
      console.log('返回的', res.code);
      if (res.code === 0) {
        // 显示成功通知
        notifySuccess();
        router.go(-1);
        form.value = { title: '', content: '' }; // 重置表单
        // if (refreshDiscussions) {
        //   refreshDiscussions();
        // }
      } else {
        // 显示错误通知
        notifyError();
      }
    });
  };
  const notifySuccess = () => {
  instance.appContext.config.globalProperties.$notify({
    title: '发帖成功！',
    message: '发帖成功，已为您跳转到首页，祝您开心:)',
    type: 'success',
    offset: 100,
    duration: 2500
  });
};

const notifyError = () => {
  instance.appContext.config.globalProperties.$notify.error({
    title: '发帖失败！',
    message: '发生了未知错误，非常抱歉！',
    offset: 100
  });
};
  onMounted(() => {
    // 如果需要在组件挂载时执行逻辑，可以在这里添加
    courseId.value=route.params.course_id || 0;
  });
  </script>
  
  <style lang="scss" scoped>
  .create-post {
    position: relative;
    left: 0;
    right: 0;
    top: 200px;
    width: 750px;
    margin: auto;
    .container {
      xwidth: 750px;
      box-sizing: border-box;
      margin-right: 160px;
      background-color: white;
      border: 1px solid gray;
      .header {
        font-size: 20px;
        font-weight: 600;
        text-align: center;
        width: 100%;
        padding: 10px;
      }
      .close {
        position: absolute;
        right: 160px;
        top: 0;
        .el-icon-close {
          font-size: 20px;
        }
      }
      form {
        padding: 20px;
        .cancel {
          margin-left: 100px;
        }
      }
    }
  }
  </style>
  