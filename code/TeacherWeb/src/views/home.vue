<template>
    <div class="home-content">
      <!-- 欢迎部分，带动画效果 -->
      <el-row justify="center" class="welcome-section" v-scroll-fade>
        <el-col :span="16">
          <el-card shadow="hover" class="welcome-card">
            <h1 class="welcome-title">欢迎来到教师管理系统</h1>
            <p class="welcome-text">在这里，您可以高效地管理教师信息和教学资源。</p>
          </el-card>
        </el-col>
      </el-row>
  
      <!-- 功能介绍部分，带动态卡片效果 -->
      <el-row justify="center" class="features-section" :gutter="20">
        <el-col :span="16">
          <el-card shadow="hover" class="features-card">
            <h2 class="features-title">功能介绍</h2>
            <el-row :gutter="20">
              <el-col :span="12" v-for="(feature, index) in features" :key="index">
                <el-card shadow="always" class="feature-item" v-fade-in="index * 0.2">
                  <el-icon :class="feature.icon" class="feature-icon" />
                  <span class="feature-text">{{ feature.text }}</span>
                </el-card>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </template>
  
  <script>
  import { ElRow, ElCol, ElCard, ElIcon } from "element-plus";
  
  export default {
    name: "Home",
    components: {
      ElRow,
      ElCol,
      ElCard,
      ElIcon,
    },
    directives: {
      scrollFade: {
        mounted(el) {
          const observer = new IntersectionObserver(
            ([entry]) => {
              entry.isIntersecting
                ? el.classList.add("fade-in")
                : el.classList.remove("fade-in");
            },
            { threshold: 0.5 }
          );
          observer.observe(el);
        },
      },
      fadeIn: {
        beforeMount(el, binding) {
          el.style.transition = `opacity 0.5s ease ${binding.value}s`;
          el.style.opacity = 0;
        },
        mounted(el) {
          setTimeout(() => {
            el.style.opacity = 1;
          }, 50);
        },
      },
    },
    data() {
      return {
        features: [
          { text: "查看和管理教师信息", icon: "el-icon-s-custom" },
          { text: "添加、编辑、删除教师资料", icon: "el-icon-edit-outline" },
          { text: "安排课程和时间表", icon: "el-icon-date" },
          { text: "生成统计报告", icon: "el-icon-data-analysis" },
          { text: "与教师沟通、发布通知", icon: "el-icon-message" },
        ],
      };
    },
  };
  </script>
  
  <style scoped>
  @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');
  
  .home-content {
    font-family: 'Roboto', sans-serif;
    padding: 20px;
    background-color: f5f5f5; /* 更浅的暖色背景，适应深色导航栏 */
  }
  
  .welcome-section {
    margin-bottom: 30px;
  }
  
  .welcome-card {
    background-color: #ffffff;
    border-radius: 15px;
    padding: 20px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.08); /* 微调阴影，使其与导航栏协调 */
  }
  
  .welcome-title {
    font-size: 2.5em;
    font-weight: 700;
    text-align: center;
    color: #3E2723; /* 更深的暖棕色标题 */
  }
  
  .welcome-text {
    font-size: 1.2em;
    text-align: center;
    color: #5D4037; /* 暖棕色文字，适应深色导航栏 */
  }
  
  .features-section .features-card {
    padding: 10px;
    border-radius: 15px;
    background-color: #ffffff;
  }
  
  .features-title {
    font-size: 2em;
    font-weight: 700;
    text-align: center;
    margin-bottom: 20px;
    color: #3E2723; /* 深棕色标题 */
  }
  
  .feature-item {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    padding: 20px;
    border-radius: 15px;
    background-color: #ffffff;
    transition: transform 0.3s ease, box-shadow 0.3s ease, background-color 0.3s ease;
  }
  
  .feature-item:hover {
    transform: translateY(-5px);
    box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.15); /* 更深的阴影 */
    background-color: #ffffff; /* hover时的浅暖色背景 */
  }
  
  .feature-icon {
    font-size: 30px;
    color: #D35400; /* 橙棕色图标，与深色导航栏相协调 */
  }
  
  .feature-text {
    font-size: 1.1em;
    margin-top: 10px;
    color: #5D4037; /* 中性暖棕色文字 */
  }
  
  /* 动画样式 */
  .fade-in {
    opacity: 1 !important;
    transform: translateY(0) !important;
  }
  
  [v-fade-in] {
    opacity: 0;
    transform: translateY(20px);
  }
  
  [v-scroll-fade] {
    opacity: 0;
    transition: opacity 0.5s ease, transform 0.5s ease;
  }
  </style>
  