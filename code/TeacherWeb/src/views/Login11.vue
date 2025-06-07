<template>
  <div class="login">
    <div class="box">
      <h2>学生管理系统</h2>

      <!-- 登录表单 -->
      <div v-if="activeTab === 'login'">
        <el-form
          size="small"
          ref="ruleFormRef"
          style="max-width: 600px"
          :model="loginForm"
          status-icon
          :rules="loginRules"
          label-width="60px"
          class="demo-ruleForm"
        >
          <el-form-item label="身份" prop="role">
            <el-select v-model="loginForm.role" placeholder="请选择身份">
              <el-option label="学生" :value="1"></el-option>
              <el-option label="教师" :value="2"></el-option>
              <el-option label="管理员" :value="3"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="邮箱" prop="email">
            <el-input v-model="loginForm.email" type="email" autocomplete="off" />
          </el-form-item>

          <el-form-item label="密码" prop="password">
            <el-input v-model="loginForm.password" type="password" autocomplete="off" />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" round @click="submitLogin">登录</el-button>
            <el-button type="info" plain round @click="toggleTab">注册</el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 注册表单 -->
      <div v-else>
        <el-form
          size="small"
          ref="ruleFormRef"
          style="max-width: 600px"
          :model="registerForm"
          status-icon
          :rules="registerRules"
          label-width="60px"
          class="demo-ruleForm"
        >
          <el-form-item label="身份" prop="role">
            <el-select v-model="registerForm.role" placeholder="请选择身份">
              <el-option label="学生" :value="1"></el-option>
              <el-option label="教师" :value="2"></el-option>
              <el-option label="管理员" :value="3"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="邮箱" prop="email">
            <el-input v-model="registerForm.email" type="email" autocomplete="off" />
          </el-form-item>

          <el-form-item label="验证码" prop="verification_code">
            <el-input
              v-model="registerForm.verification_code"
              autocomplete="off"
              placeholder="请输入验证码"
              style="width: calc(100% - 110px); display: inline-block; margin-right: 10px;"
            />
            <el-button type="primary" @click="sendVerificationCode" style="width: 100px;">发送验证码</el-button>
          </el-form-item>

          <el-form-item label="密码" prop="password">
            <el-input v-model="registerForm.password" type="password" autocomplete="off" />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" round @click="submitRegister">注册</el-button>
            <el-button type="info" plain round @click="toggleTab">返回登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import type { FormInstance, FormRules } from 'element-plus';
import { $Login, $emailVerify, $register} from '../api/admin';
import { useRouter } from 'vue-router';
import { ElNotification } from 'element-plus';
import { useUserStore } from'../store/user';

const router = useRouter();
const ruleFormRef = ref<FormInstance>();
const activeTab = ref<'login' | 'register'>('login'); // 初始为登录表单
const useStore= useUserStore();

// 切换表单
const toggleTab = () => {
  activeTab.value = activeTab.value === 'login' ? 'register' : 'login';
};

// 登录表单数据和规则
const loginForm = reactive({
  role: 2,
  email: '',
  password: ''
});
const loginRules = reactive<FormRules<typeof loginForm>>({
  role: [{ required: true, message: '请选择身份', trigger: 'change' }],
  email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
});

// 注册表单数据和规则
const registerForm = reactive({
  role: 2,
  email: '',
  verification_code:0,
  password: ''
});
const registerRules = reactive<FormRules<typeof registerForm>>({
  role: [{ required: true, message: '请选择身份', trigger: 'change' }],
  email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
  verification_code: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
});

// 登录功能
const submitLogin = () => {
  if (!ruleFormRef.value) return;
  ruleFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const ret = await $Login(loginForm);
        if (ret) {
        
          
          //设置信息
          useStore.setUser(ret.data); 
          console.log("打印" , useStore.user); // 输出用户数据
          console.log(useStore.token); // 输出 token

          //
          router.push('/index/home');
        }
      } catch (error) {
        console.error('登录失败:', error);
      }
    } else {
      console.log('提交失败!');
    }
  });
};

// 注册功能
const submitRegister = async () => {
  if (!ruleFormRef.value) return;
  ruleFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        // 直接调用 $register，并传递 registerForm 的各个字段
        await $register(
          registerForm.verification_code,
          registerForm.email,
          registerForm.password,
          registerForm.role
        );
        // 注册成功后不切换到登录界面，也不做其他处理
      } catch (error) {
        console.error('注册失败:', error);
        // 此处不再显示通知，因为 $register 方法内已包含通知逻辑
      }
    } else {
      console.log('注册表单验证失败!');
    }
  });
};


// 发送验证码功能
const sendVerificationCode = async () => {
  if (!registerForm.email || !registerForm.role) {
    ElNotification({
      title: '通知',
      message: '请先填写邮箱和选择身份',
      type: 'warning',
    });
    return;
  }
  
  try {
    await $emailVerify(registerForm.email, registerForm.role);
  } catch (error) {
    console.error('验证码发送失败:', error);
  }
};
</script>

<style scoped lang="scss">
.login {
  width: 100vw;
  height: 100vh;
  background: linear-gradient(to bottom right, #b3d1ff, #d1b3ff);
  display: flex;
  justify-content: center;
  align-items: center;

  .box {
    width: 400px;
    padding: 20px;
    border: 2px solid #6a1b9a;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);

    h2 {
      color: #6a1b9a;
      font-size: 24px;
      text-align: center;
      margin-bottom: 20px;
    }

    :deep(.el-form-item__label) {
      color: #333;
    }
  }
}
</style>
