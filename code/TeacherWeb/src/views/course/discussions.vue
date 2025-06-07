<template>
    <div class="discussions">
      <div class="forum-content">
        <div class="forum-post">
          <div>
            <div class="welcome">欢迎来到讨论区</div>
            <div class="message">请发布与课程相关的内容~</div>
            <el-button type="primary" @click="goPost">发布新帖子</el-button>
          </div>
        </div>
        <div class="content-container">
          <div class="discussions" v-for="(item) of discussions" :key="item.discussion_id">
            <content-item :discussion="item"></content-item>
          </div>
        </div>
      </div>
    </div>
  </template>
  
<script lang="ts" setup >
  import { ref, onMounted, watch ,onBeforeMount, provide } from 'vue';
  import { useRouter , useRoute } from 'vue-router'; 
  import ContentItem from '../../components/ContentItem.vue';
  import { getDiscussions } from '../../api/post';

  interface Discussion {
    discussion_id: number;
    discussion_title: string;
    discussion_content: string;
    created_time: string;
    poster: {
      user_id: number;
      user_name: string;
      user_avatar: string;
      user_role: number;
    };
  }
  const discussions = ref<Discussion[]>([]);
  const courseId = ref(0);
  const router = useRouter();
  const route = useRoute();

  onBeforeMount(() => {
  courseId.value =Number(route.params.course_id);
  console.log('courseId.value:', courseId.value);
});
  const getData = async () => {
  try {
    const res = await getDiscussions(courseId.value);
    console.log('res:', res.data);
    discussions.value = res.data.discussions;
  } catch (error) {
    console.error('Failed to fetch discussions:', error);
  }
};

onMounted(() => {
  getData();
  // refreshDiscussions();
});

watch(() => courseId.value, (newVal) => {
  if (newVal !== 0) {
    getData();
  }
});

const goPost = () => {
  if (discussions.value) {
    // 路由跳转逻辑
    router.push({
      path:  `/index/${courseId.value}/creatediscussion` 
    }).then(() => {
      // 路由跳转之后重新获取帖子列表
      getData();
    });
  } else {
    console.error('Discussions data is undefined');
  }
};

//刷新讨论区以获取新发布的贴子
const refreshDiscussions = async () => {
  try {
    const res = await getDiscussions(courseId.value);
    discussions.value = res.data.discussions;
  } catch (error) {
    console.error('Failed to fetch discussions:', error);
  }
};
provide('refreshDiscussions', refreshDiscussions);

</script>
  
  <style lang="scss" scoped>
  .forum-content {
    padding-top: 30px;
    min-width: 490px;
    max-width: 800px;
    position: relative;
    left: 0;
    right: 0;
    margin: auto;
  }
  
  .forum-post {
    height: 150px;
    background-color: white;
    width: 800px;
    margin-bottom: 20px;
    text-align: center;
    .welcome {
      padding-top: 15px;
      font-size: 30px;
      padding-bottom: 10px;
    }
    .message {
      font-size: 16px;
      padding-bottom: 5px;
    }
  }
  </style>