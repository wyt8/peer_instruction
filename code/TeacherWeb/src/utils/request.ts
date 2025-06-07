import axios from "axios";
import {baseURL_dev} from '../config/baseURL.ts'
//接口
//初始化axios对象 
  const   instance = axios.create({
    baseURL: baseURL_dev,
    timeout: 1000,
    headers: {'X-Custom-Header': 'foobar'}
  });



// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    // 在发送请求之前做点什么
    const token = localStorage.getItem('token'); // 获取本地存储的 Token
    if (token) {
      config.headers.Authorization = `Bearer ${token}`; // 添加 Token 到请求头
    }
    console.log("请求拦截器生效:", config);
    return config;
  },
  (error) => {
    // 对请求错误做点什么
    console.error("请求拦截器错误:", error);
    return Promise.reject(error);
  }
);

// 响应拦截器
instance.interceptors.response.use(
  (response) => {
    // 对响应数据做点什么
    console.log("响应拦截器返回的数据:", response);
    return response;
  },
  (error) => {
    // 对响应错误做点什么
    console.error("响应拦截器错误:", error);
    // 可根据状态码进行全局错误处理
    if (error.response?.status === 401) {
      console.warn("用户未授权或 Token 失效，请重新登录");
      // 可以触发退出登录逻辑或跳转到登录页面
    }
    return Promise.reject(error);
  }
);
















export const $post = async (url: string, params: object = {}, config: object = {}) => {
  try {
    // 合并 params 和自定义配置
    let { data } = await instance.post(url, params, config);
    return data;
  } catch (error) {
    console.error(`POST 请求失败: ${url}`, error);
    throw error;
  }
};
  
export const $get = async (url: string, params: object = {}) => {
  try {
    let { data } = await instance.get(url, { params });
    return data;
  } catch (error) {
    console.error(`GET 请求失败: ${url}`, error);
    throw error; // 保留错误抛出，方便调用处处理
  }
};
  
  export const $delete = async (url: string, params: object = {}) => {
    try {
      let { data } = await instance.delete(url, { data: params });
      return data;
    } catch (error) {
      console.error(`DELETE 请求失败: ${url}`, error);
      throw error;
    }
  };
  