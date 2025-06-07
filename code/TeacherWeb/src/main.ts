import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import { createPinia } from 'pinia'


//导入路由
import router from './router'
import ElementPlus from 'element-plus'

import 'element-plus/dist/index.css'



const app=createApp(App)
app.use(createPinia())
.use(router)
.use(ElementPlus)




app.mount('#app')
