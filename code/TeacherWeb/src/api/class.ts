import { $post, $get } from "../utils/request.ts";
//mock 文件
import '../mock/mock';


// 获取课堂列表
export const fetchClassList = async (courseId: string) => {
    const response = await $get(`/class/list?course_id=${courseId}`);
    return response;
};


// 添加课堂
export const createClass = async (courseId: number, formCreateClass: any) => {
    const response = await $post(`/class/create`, {
        course_id: courseId,
        class_name: formCreateClass.class_name,
        start_time: formCreateClass.start_time,
        end_time: formCreateClass.end_time,
    });
    return response;
};


// 添加题目
export const addExercise = async (classId: number, exerciseList: Array<number>) => {
    const response = await $post(`/class/exercise/add`, {
        class_id: classId,
        exercise_list: exerciseList,
    });
    return response;
};

// 发布课堂
export const publishClass = async (classId: number) => {
    const response = await $post(`/class/publish`, {
        class_id: classId,
    });
    return response;
};


// 发布签到
export const publishSign = async (classId: number) => {
    const response = await $post(`/class/sign/publish`, {
        class_id: classId,
    });
    return response;
};

// 发布测试
export const publishExercise = async (classId: number, exerciseId: number, testId: number) => {
    const response = await $post(`/class/exercise/publish`, {
        class_id: classId,
        exercise_id: exerciseId,
        test_id: testId,
    });
    return response;
};

// 查询课堂详情
export const fetchClassDetail = async (classId: number) => {
    const response = await $get(`/class/detail?class_id=${classId}`);
    console.log(response.data.exercise_list)
    // response.exercise_list.map((value) => {return {"exercise_id": value};})
    for (let i = 0; i < response.data.classes.exercise_list.length; i++) {
        response.data.classes.exercise_list[i] = {"exercise_id": response.data.classes.exercise_list[i] }
      }
    console.log("141431"+response)
    return response;
};

// 查询教师可以查看的题目列表
export const fetchExerciseList = async (courseId: number) => {
    const response = await $get(`/class/questions?course_id=${courseId}`);
    return response;
};

// 查询学生列表
export const fetchStudentList = async (classId: number) => {
    const response = await $get(`/class/students?class_id=${classId}`);
    return response;
};

// 查询课堂报告
export const fetchClassReview = async (courseId: number, classId: number) => {
    const response = await $get(`/class/review?course_id=${courseId}&class_id=${classId}`);
    return response;
};

// 结束课堂
export const endClass = async (courseId: number, classId: number) => {
    const response = await $get(`/class/end?course_id=${courseId}&class_id=${classId}`);
    return response;
};

