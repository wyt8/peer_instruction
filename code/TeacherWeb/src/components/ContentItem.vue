<template>
    <div class="content-item"@click="clickItem">
      <div class="avatar">
        <img :src="avatar" alt="" srcset="" />
      </div>
      <div class="discussion">
        <div class="title">{{ discussion.discussion_title }}</div>
        <div class="detail">
          <span class="user">{{ discussion.poster.user_name}}</span>
          <span class="time">{{ discussion.created_time }}</span>
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
import { defineProps,computed,toRefs,onBeforeMount,ref } from 'vue'
import { useRouter  , useRoute} from 'vue-router';
const props = defineProps({
  discussion: {
    type: Object,
    required: true
  }
});

const courseId = ref(0);
const { discussion } = toRefs(props);
const avatar = computed(() => discussion.value.poster.user_avatar);
const router = useRouter();
const route = useRoute();

onBeforeMount(() => {
  courseId.value =Number(route.params.course_id);
  console.log('courseId.value:', courseId.value);
});

const clickItem = () => {
  const { discussion_id, discussion_title, discussion_content, created_time, poster } = discussion.value;
  console.log('discussion:', discussion.value);
  console.log('Navigating to review with discussionId:', discussion_id);
  router.push({
    path:`/index/${courseId.value}/discussions/${discussion_id}/review`,
    query: {
      title: discussion_title,
      content: discussion_content,
      createdTime: created_time,
      posterName: poster.user_name,
      posterAvatar: poster.user_avatar
    }
  });
};
</script>
  
  <style lang="scss" scoped>
  .content-item {
    height: 70px;
    padding: 10px;
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    background-color: white;
    border: 1px solid rgb(188, 188, 188);
    box-sizing: border-box;
    margin-bottom: -1px;
    cursor: pointer;
    .avatar {
    height: 50px;
    width: 50px;
    overflow: hidden;
    img {
      transform: translate(-10px, -10px);
      width: 70px;
      object-fit: fill;
    }
  }
    .discussion{
      height: 50px;
      margin-left: 10px;
      background-color: white;
      flex: 1;
      .title {
        line-height: 30px;
        font-size: 16px;
        color: #778077;
      }
      .detail {
      line-height: 20px;
      font-size: 12px;
      color: #999999;
      .user {
        color: rgb(119, 128, 135);
      }
      .time {
        margin-left: 10px;
        font-size: 12px;
        color: #cccccc;
      }
    }
    }
    .counts {
    height: 50px;
    width: 70px;
    position: relative;
    .count {
      position: absolute;
      top: 17px;
      right: 5px;
      width: 36px;
      height: 16px;
      display: inline-block;
      background-color: #aab0c6;
      border-radius: 8px;
      color: #ffffff;
      font-size: 14px;
      text-align: center;
      vertical-align: center;
      font-weight: 600;
      line-height: 16px;
    }
  }
  }
  </style>
  