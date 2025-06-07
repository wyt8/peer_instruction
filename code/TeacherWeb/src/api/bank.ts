import { $get ,$post} from '../utils/request';

interface CreateQuestionData {
  quName: string;
  content: string;
  quOptions: string[];
  quAnswer: string;
  scorevalue: number;
  quType: string;
}

//查询题库列表
export async function getBanks(teacherId?: number) {
  try {
    const response = await $get(`/banks/query_bank/${teacherId }`,  );
    console.log('API响应:', response.data); // 打印API响应数据
    return response.data;
  } catch (error) {
    console.error('获取题库列表失败', error);
    throw error; // 抛出错误
  }
}
//查询题目列表
export async function getQuestionsByBankId(bankId: number) {
  try {
    const response = await $get(`/banks/${bankId}/questions_query`);
    console.log('API响应:', response.data); // 打印API响应数据
    return response.data;
  } catch (error) {
    console.error('获取题目列表失败', error);
    throw error; // 抛出错误
  }
}
//创建题目

export async function createQuestion(bankId: number, questionData: CreateQuestionData) {
  try {
    const response = await $post(`/banks/${bankId}/create_question`, questionData);
    console.log('API响应:', response.data);
    return response.data;
  } catch (error) {
    console.error('创建题目失败', error);
    throw error;
  }
}

//查看、修改题目

export async function updateQuestion(bankId: number, questionId: number, questionData: CreateQuestionData) {
  try {
    const response = await $post(`/banks/${bankId}/update_question/${questionId}`, questionData);
    if (response && response.data) {
      console.log('API响应:', response.data);
      return response.data;
    } else {
      // 如果后端没有返回数据，可以返回一个默认对象
      return { success: true, message: '题目更新成功' };
    }
  } catch (error) {
    console.error('更新题目失败', error);
    throw error;
  }
}

//删除题目

export async function deleteQuestion(bankId: number, questionId: number) {
  try {
    const response = await $post(`/banks/${bankId}/delete_question/${questionId }`,);
    if (response && response.data) {
      console.log('API响应:', response.data);
      return response.data;
    } else {
      // 如果后端没有返回数据，可以返回一个默认对象
      return { success: true, message: '题目删除成功' };
    }
  } catch (error) {
    console.error('删除题目失败', error);
    throw error;
  }
}