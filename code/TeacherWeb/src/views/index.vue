<template>
  <div class="chatgpt-layout">
    <!-- 左侧导航栏 -->
    <div class="sidebar">
      <h2 class="sidebar-title">系统管理面板</h2> <!-- 标题在导航栏内 -->
      <el-menu
        router
        active-text-color="#ffd04b"
        background-color="#142334"
        class="el-menu-vertical-demo"
        :default-active="currentRoute"
        text-color="#fff"
        @open="handleOpen"
        @close="handleClose"
      >
        <el-sub-menu index="1">
          <template #title>
            <el-icon><Pointer /></el-icon>
            <span>管理</span>
          </template>
          <el-menu-item index="/index/main">课程创建</el-menu-item>
          <el-menu-item index="/index/role">加入课程</el-menu-item>
          <el-menu-item index="/index/courseList">课程列表</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="2">
          <template #title>
            <el-icon><User/></el-icon>
            <span>学生</span>
          </template>
          <el-menu-item index="/index/add">学生管理</el-menu-item>
          <el-menu-item index="/index/data">学生信息统计</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="3">
          <template #title>
            <el-icon><Aim /></el-icon>
            <span>课堂</span>
          </template>
          <el-menu-item index="/index/classList">课堂管理</el-menu-item>
          <el-menu-item index="/index/classDetail">课堂详情</el-menu-item>
        </el-sub-menu>
      

        <el-sub-menu index="4">
            <template #title>
                <el-icon><Star /></el-icon>
              <span>题库</span>
            </template>
            <el-menu-item index="/index/viewbank">查看题库</el-menu-item>
          </el-sub-menu>
      </el-menu>
    </div>

    <!-- 主内容区域 -->
    <div class="main-content">
      <div class="top">
        <!-- 顶部导航栏 -->
        <el-menu
          router
          class="el-menu-demo"
          mode="horizontal"
          background-color="#e2e1e4"
          text-color="#783178"
          active-text-color="#322f3b"
        >
          <el-menu-item index="/index/home">
            <el-icon style="color: #409efc;"><House /></el-icon>
            首页
          </el-menu-item>
          <el-menu-item index="/index/courseList">
            <el-icon style="color: #409efc;"><ChatDotRound /></el-icon>
            消息
          </el-menu-item>
          <el-sub-menu index="3">
            <template #title>工作区</template>
            <el-menu-item index="2-1">item one</el-menu-item>
            <el-menu-item index="2-2">item two</el-menu-item>
            <el-menu-item index="2-3">item three</el-menu-item>
            <el-sub-menu index="2-4">
              <template #title>item four</template>
              <el-menu-item index="2-4-1">item one</el-menu-item>
              <el-menu-item index="2-4-2">item two</el-menu-item>
              <el-menu-item index="2-4-3">item three</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
        </el-menu>

        <!-- 用户状态信息 -->
<div class="user-status">
  <el-avatar
    v-if="userStore.user.user_avatar"
    :src="userStore.user.user_avatar"
    size="medium"
    shape="circle"
    fit="cover"
    class="user-avatar"
  />
  <div class="user-details">
    <span class="user-name">{{ userStore.user.user_name || '未登录' }}</span>
    <span class="user-role">教师</span> <!-- 可动态设置身份 -->
  </div>
  <el-button
    v-if="userStore.token"
    size="small"
    type="danger"
    @click="logout"
    class="logout-btn"
  >
    退出
  </el-button>
</div>
      </div>

      <div class="content">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, computed, ref, watch } from "vue";
import { useUserStore } from "../store/user"; // 引入 Pinia 用户信息 Store
import { User, Star, Aim, House, ChatDotRound, Pointer } from "@element-plus/icons-vue"; // 引入图标组件
import { useRouter, useRoute } from 'vue-router';

function handleOpen(key: string, keyPath: string[]) {
  console.log(`Menu ${key} opened`, keyPath);
}

function handleClose(key: string, keyPath: string[]) {
  console.log(`Menu ${key} closed`, keyPath);
}

// 初始化 Pinia Store
let userStore = useUserStore();
let router = useRouter();
let route = useRoute();

// 动态设置 default-active
let currentRoute = ref(route.path);

// 监听路由变化
watch(route, (newRoute) => {
  currentRoute.value = newRoute.path;
}, { immediate: true });

// 加载用户信息
onMounted(() => {
  userStore.loadUser(); // 加载本地存储的用户信息
  if (!userStore.token) {
    router.push('/'); // 如果未登录，重定向到登录页面
  }
});

// 用户登出
const logout = () => {
  userStore.clearUser();
  router.push('/'); // 退出后重定向到登录页面
};
</script>

<style scoped lang="scss">
.chatgpt-layout {
  display: flex;
  height: 100vh; /* 占满整个视窗高度 */

  /* 左侧导航栏 */
  .sidebar {
    width: 250px;
    background-color: #142334;
    display: flex;
    flex-direction: column;
    height: 100%; /* 占满页面高度 */
  }

  .sidebar-title {
    color: white;
    font-size: 20px;
    text-align: center;
    padding: 10px 0;
    border-bottom: 1px solid #e0e0e0; /* 底部分割线 */
  }

  .el-menu {
    flex: 1; /* 占据剩余空间 */
    border-right: none;
    
  }

  /* 主内容区域 */
  .main-content {
    flex: 1; /* 占用剩余空间 */
    height: 100%; /* 确保高度填满父容器 */
    background-color: #f5f5f5;
    display: flex;
    flex-direction: column; /* 垂直布局 */
    align-items: stretch; /* 确保子元素拉伸 */
    justify-content: flex-start; /* 从顶部开始对齐 */
    padding: 0px; /* 添加内边距 */
    overflow: auto;
  }

  .top {
    height: 60px;
    font-size: 20px;
    margin-bottom: 5px;
    background-color:   #dedbdbc4;
    display: flex;
    align-items: center; /* 垂直居中 */
    padding: 0;
  }

  .user-status {
  display: flex;
  align-items: center;
  margin-left: auto; /* 将用户信息内容靠右 */
  gap: 10px; /* 头像和文字之间的间隔 */
  

  .user-avatar {
    border: 2px solid #783178; /* 添加边框以强调头像 */
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2); /* 添加阴影 */
    
  }

  .user-details {
    display: flex;
    flex-direction: column;
    align-items: flex-start; /* 左对齐内容 */
    color: #333; /* 修改文字颜色 */

    .user-name {
      font-size: 16px;
      font-weight: bold;
    }

    .user-role {
      font-size: 12px;
      color: #888; /* 显示角色的辅助信息 */
    }
  }

  .logout-btn {
    background-color: #ff4d4f;
    color: #ffffff;
    font-weight: bold;
    border-radius: 4px;
    box-shadow: 0 2px 6px rgba(255, 77, 79, 0.2);
    transition: all 0.3s;
  }

  .logout-btn:hover {
    background-color: #d9363e;
  }
}

  .content {
    flex: 1;
    padding: 0px;
  }
}
</style>
