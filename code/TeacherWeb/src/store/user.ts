import { defineStore } from 'pinia';

interface UserData {
  user_id: number;       // 用户 ID
  user_name: string;     // 用户名
  user_avatar: string;   // 用户头像
  token: string;         // 用户登录的 token
}

export const useUserStore = defineStore('user', {
  state: () => ({
    user: {
      user_id: 1,     // 用户 ID
      user_name: '',     // 用户名
      user_avatar: '',   // 用户头像
    },
    token: '',           // 登录后的 token
  }),

  actions: {
    // 设置用户信息
    setUser(userData: UserData) {
      
      
      this.user = {
        user_id: userData.user_id,
        user_name: userData.user_name,
        user_avatar: userData.user_avatar,
      };
      this.token = userData.token; // 保存 token

      localStorage.setItem('user', JSON.stringify(this.user)); // 存储用户数据
      localStorage.setItem('token', userData.token); // 将 token 存储到本地存储
    },


// 加载本地存储的用户信息
loadUser()   {
  const user = localStorage.getItem('user');
  const token = localStorage.getItem('token');
  
  if (user && token) {
    this.user = JSON.parse(user);
    this.token = token;
  }
},
 

    // 清除用户信息
    clearUser()   {
      this.user = {
        user_id: 1,
        user_name: '',
        user_avatar: '',
      };
      this.token = '';
      localStorage.removeItem('token'); // 清除本地存储的 token
    },
  },
});
