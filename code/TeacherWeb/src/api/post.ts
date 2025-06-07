import { $get, $post } from '../utils/request';

// 获取课程讨论区数据
export const getDiscussions = async (courseId: number, lastDiscussionId?: number, num?: number) => {
    const params = {
      last_discussion_id: lastDiscussionId,
      num: num
    };
    return $get(`/courses/${courseId}/discussions`, params);
};
  
// 向课程讨论区发帖
export const addPosts = async (courseId: number, title: string, content: string, posterId: string, posterRole: string) => {
  const response = await $post(`/courses/${courseId}/discussions`, {
    discussion_title: title,
    discussion_content: content,
    poster_id: posterId,
    poster_role: posterRole
  });
  console.log('Add post response', response);
  return response;
};

//获取帖子回复
export const getReviews = async (courseId: number, discussionId: number, lastReviewId?: string, num?: string) => {
    return $get(`/courses/${courseId}/discussions/${discussionId}/reviews`);
  };

// 在帖子下添加回复
export const createReview= async (courseId: number,discussionId: number,reviewContent: string,) => {
    const response = await $post(`/courses/${courseId}/discussions/${discussionId}/reviews`, {
      review_content:reviewContent
    });
    console.log('Add comment response:', response);
    return response;
};

