<template>
  <div class="reviews-item">
    <div class="avatar">
      <img :src="avatar" alt="" srcset="" />
    </div>
    <div class="message">
      <span class="name">{{ name }}</span>
      <span class="time">{{ time }}</span>
    </div>
    <div class="comments">
      {{ comment.review_content }}
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed,toRefs ,onMounted} from 'vue'
import parseTime from '../utils/parseTime';

const props = defineProps({
comment: {
  type: Object,
  required: true
}
});

const { comment } = toRefs(props);

const avatar = computed(() => comment.value.reviewer.user_avatar);
const name = computed(() => comment.value.reviewer.user_name);
const time = computed(() => parseTime(comment.value.created_time));

const getDate = async () => {
// 如果需要从API获取更多信息，可以在这里添加API调用
};

onMounted(() => {
getDate();
});
</script>


<style lang="scss" scoped>
.reviews-item {
  position: relative;
  padding: 10px 10px 10px 75px;
  box-sizing: border-box;
  border-bottom: 1px solid gray;
  .avatar {
    position: absolute;
    width: 50px;
    height: 50px;
    top: 10px;
    left: 7px;
    overflow: hidden;
    img {
      width: 70px;
      height: 70px;
      transform: translate(-10px, -10px);
    }
  }
  .message {
    height: 20px;
    line-height: 20px;
    .name {
      font-size: 14px;
      color: #778087;
    }
    .time {
      margin-left: 10px;
      font-size: 12px;
      color: #cccccc;
    }
  }
  .comments {
    padding-top: 10px;
    font-size: 14px;
    color: #444444;
  }
}
</style>