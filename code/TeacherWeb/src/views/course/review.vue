<template>
    <div class="review">
      <div class="header-content">
        <div class="header">
          <div class="title">{{title }}</div>
          <div class="message">
            <span class="name">{{name  }}</span>
            <span class="time">{{time }}</span>
          </div>
  
          <div class="avatar">
            <img :src="avatar as unknown as string" alt="" srcset="" />
          </div>
        </div>
        <div class="post-content">
          <div v-for="(line, index) of contentLines" :key="index">
            <p>{{ line }}</p>
          </div>
        </div>
      </div>
      <div class="comments">
        <div class="top">
          <span>{{ count }}条回复 · </span>
          <span>{{ nowTime }}</span>
        </div>
        <div class="comment" v-for="(item, index) in comments" :key="item.review_id">
          <review-item :comment="item"></review-item>
        </div>
      </div>
      <div class="add-comments">
        <create-review
          
        ></create-review> 
      </div>
    </div>
  </template>
  
<script lang="ts" setup>
import {computed, ref ,onMounted} from 'vue'
//import { useRoute} from 'vue-router';
import {getReviews} from '../../api/post'
import parseTime from '../../utils/parseTime'
import reviewItem from '../../components/reviewItem.vue'
import createReview from '../../components/createReview.vue'
import { useRoute } from 'vue-router'

interface Review {
  review_id: number;
  review_content: string;
  created_time: string;
  reviewer: {
    user_id: number;
    user_name: string;
    user_avatar: string;
    user_role: number;
  };
}

const route = useRoute();
const courseId  = ref(Number(route.params.course_id));
const discussionId  = ref(Number(route.params.discussion_id));

const title = computed(() => route.query.title || '');
const content = computed(() => route.query.content as string);
const time = computed(() => route.query.createdTime || '');
const name = computed(() => route.query.posterName || '');
const avatar = computed(() => route.query.posterAvatar || '');

const userId = ref(0);

const comments = ref<Review[]>([]);
const nowTime = new Date();
const count = ref(0);

const contentLines = computed(() => {
  // 使用类型断言将content.value断言为字符串
  return (content.value as string).split('\n');
});

const init = async () => {
  try {
    const reviewsResponse = await getReviews(courseId.value, discussionId.value);
    if (reviewsResponse && reviewsResponse.data) {
      comments.value = reviewsResponse.data.reviews;
      console.log('comments', comments.value);
      count.value = reviewsResponse.data.reviews.length;
    } else {
      console.error('No data received from getReviews');
    }
  } catch (error) {
    console.error('Error fetching reviews:', error);
  }
}
onMounted(() => {
  init();
});
</script>
  
  <style lang="scss" scoped>
  .review {
    min-width: 490px;
    max-width: 1154px;
    position: relative;
    left: 0;
    right: 0;
    margin-top: 15px;
    margin: auto;
    padding-top: 15px;
    padding-right: 354px;
    xoverflow: hidden;
    .header {
      xheight: 100px;
      padding: 20px 10px 20px 10px;
      position: relative;
      box-sizing: border-box;
      background-color: white;
      border-bottom: 1px solid rgb(188, 188, 188);
      .title {
        height: 36px;
        xdisplay: inline-block;
        line-height: 26px;
        font-size: 24px;
        padding: 0 0 10px 0;
        box-sizing: border-box;
        color: black;
      }
      .message {
        display: inline-block;
        height: 16px;
        line-height: 16px;
        font-size: 12.5px;
        color: #999;
        .name {
          color: black;
        }
      }
      .avatar {
        position: absolute;
        top: 10px;
        right: 10px;
        height: 70px;
        width: 70px;
        xborder: 1px solid gray;
        overflow: hidden;
        img {
          transform: translate(-10px, -10px);
          width: 90px;
          object-fit: fill;
        }
      }
    }
    .post-content {
      padding: 20px 10px 20px 10px;
      box-sizing: border-box;
      background-color: white;
      width: 800px;
      font-size: 14px;
      color: black;
      line-height: 20px;
      margin-bottom: 15px;
      white-space: pre-wrap;
      p {
        text-indent: 2em;
        padding: 5px 0;
        margin: 0;
      }
    }
  
    .comments {
      font-size: 14px;
      color: #999999;
      box-sizing: border-box;
      background-color: white;
      overflow: hidden;
      .top {
        height: 42px;
        padding: 10px;
        box-sizing: border-box;
        line-height: 22px;
        box-shadow: 0 1px 1px gray;
      }
    }
  }
  </style>
  