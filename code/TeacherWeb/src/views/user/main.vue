<template>
  <div class="course-create">
    <el-card class="box-card">
      <h2>创建新课程</h2>
      <el-form
        ref="courseFormRef"
        :model="courseForm"
        :rules="rules"
        label-width="100px"
        class="course-form"
      >
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="courseForm.name" placeholder="请输入课程名称" />
        </el-form-item>

        <el-form-item label="课程图片" prop="image">
          <el-upload
            class="upload-demo"
            action="#"
            list-type="picture-card"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="courseForm.image"
            :auto-upload="true"
            :http-request="customUpload"
            :on-success="uploadSuccess"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>

        <el-form-item label="课程描述" prop="description">
          <el-input
            type="textarea"
            v-model="courseForm.description"
            placeholder="请输入课程描述"
            :rows="5"
          />
        </el-form-item>

      

        <el-form-item>
          <el-button type="primary" @click="submitForm(courseFormRef)">
            创建课程
          </el-button>
          <el-button @click="resetForm(courseFormRef)">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import type { FormInstance, FormRules, UploadProgressEvent } from 'element-plus';
import { $createCourse,$uploadImage } from '../../api/admin' // 引入 $createCourse ...方法
import { useUserStore } from '../../store/user';

interface UploadFile {
  url: string;
  [key: string]: any;  // 如果上传文件对象中有其他属性，可以在这里添加
}



// 初始化表单数据
const courseForm = reactive({
  name: '',
  image: []as UploadFile[],  // 图片上传的文件列表
  description: '',
  category: '',
});

const courseFormRef = ref<FormInstance>();

const rules = reactive<FormRules<typeof courseForm>>({
  name: [{ required: true, message: '请输入课程名称', trigger: 'blur' }],
  description: [{ required: true, message: '请输入课程描述', trigger: 'blur' }],
  category: [{ required: true, message: '请选择课程分类', trigger: 'change' }],
});

const handlePreview = (file: any) => {
  console.log('预览图片', file);
};

const handleRemove = (file: any, fileList: any) => {
  console.log('移除图片', file, fileList);
};


// 获取教师 ID
const userStore = useUserStore(); 
const teacherId =userStore.user.user_id;  // 您可以从用户上下文中获取或动态传入
const image_url = ref("")


const uploadSuccess = (body: any) => {
  console.log(typeof body)
  image_url.value = JSON.parse(body)["url"]
  console.log("body " + image_url.value)
}

// 自定义上传逻辑
const customUpload = async (option: any) => {
  // const { file } = params;

  // try {
  //   // 调用上传接口
  //   const response = await $uploadImage(file);
  //   //打印数据
  //   console.log('上传接口返回:', response);
  //   // 添加到表单的图片列表
  //   courseForm.image.push({
  //     url: response.url, // 上传后返回的图片 URL
  //   });

  //   params.onSuccess(response); // 通知 ElUpload 上传成功
  // } catch (error) {
  //   console.error('上传图片失败:', error);
  //   params.onError(error); // 通知 ElUpload 上传失败
  // }


  const xhr = new XMLHttpRequest()
  const action = "http://116.205.181.81:8081/upload_img"

  if (xhr.upload) {
    xhr.upload.addEventListener('progress', (evt) => {
      const progressEvt = evt as unknown as UploadProgressEvent
      progressEvt.percent = evt.total > 0 ? (evt.loaded / evt.total) * 100 : 0
      option.onProgress(progressEvt)
    })
  }

  const formData = new FormData()
  formData.append("img", option.file, option.file.name)

  xhr.addEventListener('error', () => {
    console.log("上传错误")
  })

  xhr.addEventListener('load', () => {
    if (xhr.status < 200 || xhr.status >= 300) {
      console.log("上传错误")
    }
    option.onSuccess(xhr.response)
    console.log("上传成功")
  })

  xhr.open(option.method, action, true)

  if (option.withCredentials && 'withCredentials' in xhr) {
    xhr.withCredentials = true
  }

  // const headers = option.headers || {}
  // if (headers instanceof Headers) {
  //   headers.forEach((value, key) => xhr.setRequestHeader(key, value))
  // } else {
  //   for (const [key, value] of Object.entries(headers)) {
  //     if (isNil(value)) continue
  //     xhr.setRequestHeader(key, String(value))
  //   }
  // }

  xhr.send(formData)
  return xhr
};



// 表单提交
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (valid) {
      try {
        // 调用 $createCourse API 方法
        const courseImageUrl = image_url.value;  // 使用上传的第一张图片 URL
        console.log(image_url.value)
        const response = await $createCourse(teacherId, courseForm.name, courseImageUrl);
        
        // 课程创建成功后，清空表单
        if (response.code === 0) {
          resetForm(formEl);
        }
      } catch (error) {
        console.error('创建课程出错:', error);
      }
    } else {
      console.log('表单验证失败');
    }
  });
};

// 表单重置
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};
</script>

<style scoped lang="scss">
.course-create {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;

  .box-card {
    width: 600px;
    padding: 20px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }

  .course-form {
    margin-top: 10px;
  }

  .el-upload {
    display: block;
    margin-bottom: 10px;
  }
}
</style>
