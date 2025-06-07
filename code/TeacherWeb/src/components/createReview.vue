<template>
    <div class="createReview">
      <div class="header">
        <div class="left">增加一条新回复</div>
      </div>
      <el-form class="form">
        <el-form-item>
          <el-input type="textarea" v-model="comment"></el-input>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="onSubmit()" class="reply"
        >回复</el-button
      >
    </div>
  </template>

<script setup>
import { ref  , getCurrentInstance} from 'vue';
import { useRoute} from 'vue-router';
import { createReview } from '../api/post'; 

const comment = ref('');
const route = useRoute();
const discussionId = Number(route.params.discussion_id);
const courseId  = Number(route.params.course_id);
const instance = getCurrentInstance();

const onSubmit = async () => {
    createReview( 
      courseId,
      discussionId, 
      comment.value, 
    ).then(res => {
      console.log('返回的', res.code);
    if (res.code === 0) {
      // 提交成功，可以在这里触发一个事件，通知父组件刷新评论列表
      console.log('Comment added successfully:', res.data);
      notifySuccess();
      // 清空评论框
      comment.value = '';
      instance.proxy.$router.go(0); // 重新加载当前页面
    } else {
      console.error('Failed to add comment:', res.data.message);
      notifyError();
    }
  });
}
const notifySuccess = () => {
  instance.appContext.config.globalProperties.$notify({
    title: '回复成功！',
    message: '回复成功，：）',
    type: 'success',
    offset: 100,
    duration: 2500
  });
};

const notifyError = () => {
  instance.appContext.config.globalProperties.$notify.error({
    title: '回复失败！',
    message: '回复了未知错误，非常抱歉！',
    offset: 100
  });
};

</script>

<style lang="scss" scoped>
.createReview {
  margin-top: 10px;
  overflow: hidden;
  background-color: white;
  .header {
    height: 42px;
    box-sizing: border-box;
    padding: 10px;
    border-bottom: 1px solid gray;
    .left {
      line-height: 22px;
      font-size: 14px;
      color: black;
    }
  }
  .form {
    padding: 15px 15px 0 15px;
    box-sizing: border-box;
  }
  .reply {
    margin-bottom: 15px;
    margin-left: 15px;
  }
}
</style>
