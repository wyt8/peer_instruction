import { createRouter, createWebHashHistory } from 'vue-router'

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      meta:{title:'登录'},
      component: () => import('../views/Login11.vue')
    },
    {
      path: '/login',
      redirect: '/'
    },
    {
      path: '/index',
      meta:{title:'系统管理面板'},
      component: () => import('../views/index.vue'),
      children:[
       {
        path:'home',
        meta:{title:'首页'},
        component: () => import('../views/home.vue'),
       },
       {
        path:'message',
        meta:{title:'消息'},
        component: () => import('../views/message.vue'),
       },
       {
        path:'main',
        meta:{title:'加入'},
        component: () => import('../views/user/main.vue'),
       },
       {
        path:'role',
        meta:{title:'创建'},
        component: () => import('../views/user/role.vue'),
       },
       {
        path:'add',
        meta:{title:'添加学生'},
        component: () => import('../views/user/add.vue'),
       },
       {
        path:'data',
        meta:{title:'学生统计'},
        component: () => import('../views/user/studentData.vue'),
       },
       {
        path:'courseList',
        meta:{title:'课程列表'},
        component: () => import('../views/course/courseList.vue'),
       },
      //课堂
       {
        path:'classList',
        meta:{title:'课程列表'},
        component: () => import('../views/class/classList.vue'),
       },
       {
        path:'classDetail',
        meta:{title:'课堂详情'},
        component: () => import('../views/class/classDetail.vue'),
       },

       //  讨论区
       {
        path:':course_id/discussions',
        component:() =>import('../views/course/discussions.vue'),
      },
      {
        path:':course_id/discussions/:discussion_id/review',
        component: () => import('../views/course/review.vue'),
      },
      {
        path:':course_id/creatediscussion',
        component: () => import('../views/course/createPost.vue'),
       },

       //题库部分
      {
        path:'viewbank',
        component: () => import('../views/bank/viewbank.vue'),
      },


      ]

    }
  ]
})

export default router
