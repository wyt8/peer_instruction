package com.zch1001.pi.controller;


import com.zch1001.pi.entity.VO.DiscussionsVO;
import com.zch1001.pi.entity.VO.ReviewsVO;
import com.zch1001.pi.service.DiscussionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
public class DiscussionController {
    @Autowired
    private DiscussionService discussionService;

    @GetMapping("/courses/{course_id}/discussions")//获取课程讨论区
    public Map<String,Object> selectDiscussions(@PathVariable("course_id") Long  course_id,Integer last_discussion_id,Integer num) {
        Map<String,Object> response = discussionService.selectDiscussions(course_id,last_discussion_id,num);
        return response;
    }

    @PostMapping("/courses/{course_id}/discussions")//向课程讨论区发帖
    public Map<String,Object> addDiscussions(HttpServletRequest request, @PathVariable("course_id") Long  course_id, @RequestBody DiscussionsVO discussionsVO) {
        Map<String,Object> response = discussionService.addDiscussions(request, course_id,discussionsVO);
        return response;
    }

    @GetMapping("/courses/{course_id}/discussions/{discussion_id}/reviews")//获取某个讨论的回复
    public Map<String,Object> selectReviews(@PathVariable("course_id") Integer  course_id,@PathVariable("discussion_id") Integer discussion_id,Integer last_review_id,Integer num) {
        Map<String,Object> response = discussionService.selectReviews(course_id,discussion_id,last_review_id,num);
        return response;
    }

    @PostMapping("/courses/{course_id}/discussions/{discussion_id}/reviews")//向课程讨论区发帖
    public Map<String,Object> addReviews(HttpServletRequest request, @PathVariable("course_id") Integer  course_id,@PathVariable("discussion_id") Integer discussion_id, @RequestBody ReviewsVO reviewsVO) {
        Map<String,Object> response = discussionService.addReviews(request, course_id,discussion_id,reviewsVO);
        return response;
    }

    @DeleteMapping("/courses/{course_id}/discussions/{discussion_id}")//删除帖子或删除回复如果is_reply=true，表示删除的是某个回复 discussion_id代表的回复的id。 如果is_reply=false 或未设置，则删除帖子discussion_id帖子的id。
    public Map<String,Object> deleteReviewsOrDiscussion(@PathVariable("course_id") Long  course_id,@PathVariable("discussion_id") Long discussion_id,Boolean is_reply ) {
        Map<String,Object> response = discussionService.deleteReviewsOrDiscussion(course_id,discussion_id,is_reply);
        return response;
    }


}
