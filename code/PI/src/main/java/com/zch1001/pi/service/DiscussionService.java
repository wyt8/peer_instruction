package com.zch1001.pi.service;

import com.zch1001.pi.entity.*;
import com.zch1001.pi.entity.VO.CourseVO;
import com.zch1001.pi.entity.VO.DiscussionsVO;
import com.zch1001.pi.entity.VO.ReviewsVO;
import com.zch1001.pi.model.StudentCoursesResponse;
import com.zch1001.pi.repository.*;
import com.zch1001.pi.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DiscussionService {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    public Map<String, Object> selectDiscussions(Long courseId, Integer lastDiscussionId, Integer num) {
        List<Discussion> all = discussionRepository.findAll();
        List<Discussion> collect = all.stream().filter(t -> t.getCourseId().equals(courseId)).sorted(Comparator.comparing(Discussion::getCreatedTime).reversed()).collect(Collectors.toList());
        /*if (null!=lastDiscussionId) {
            collect = collect.stream().filter(w -> w.getDiscussionId().equals(lastDiscussionId)).collect(Collectors.toList());
        }
        if (null!=num) {
            collect = collect.stream().limit(num).collect(Collectors.toList());
        }*/
        Map<String, Object> stringObjectHashMap = new HashMap<>();//返回数据结构
        stringObjectHashMap.put("code",0);
        stringObjectHashMap.put("msg","成功");
        Map<String, Object> stringObjectHashMap3 = new HashMap<>();

        List<Map<String, Object>> hashMaps = new ArrayList<>();
        for (Discussion discussion : collect) {
            Map<String, Object> stringObjectHashMap1 = new HashMap<>();//返回数据信息
            stringObjectHashMap1.put("discussion_id",discussion.getDiscussionId());
            stringObjectHashMap1.put("discussion_title",discussion.getDiscussionTitle());
            stringObjectHashMap1.put("discussion_content",discussion.getDiscussionContent());
            stringObjectHashMap1.put("created_time",discussion.getCreatedTime());
            Map<String, Object> stringObjectHashMap2 = new HashMap<>();
            if (discussion.getPosterRole() == 2) {
                Optional<Teacher> byId = teacherRepository.findById(discussion.getPosterId());
                stringObjectHashMap2.put("user_id",byId.get().getId());
                stringObjectHashMap2.put("user_name",byId.get().getName());
                stringObjectHashMap2.put("user_avatar",byId.get().getAvatar());
                stringObjectHashMap2.put("user_role",2);
                stringObjectHashMap1.put("poster",stringObjectHashMap2);
            }else if (discussion.getPosterRole()==3) {
                Optional<Student> byId = studentRepository.findById(Long.valueOf(discussion.getPosterId()));
                stringObjectHashMap2.put("user_id",byId.get().getId());
                stringObjectHashMap2.put("user_name",byId.get().getName());
                stringObjectHashMap2.put("user_avatar",byId.get().getAvatar());
                stringObjectHashMap2.put("user_role",3);
                stringObjectHashMap1.put("poster",stringObjectHashMap2);
            }
            hashMaps.add(stringObjectHashMap1);
        }
        stringObjectHashMap3.put("discussions",hashMaps);
        stringObjectHashMap.put("data",stringObjectHashMap3);
        return stringObjectHashMap;
    }

    public Map<String, Object> addDiscussions(HttpServletRequest request, Long courseId, DiscussionsVO discussionsVO) {
        String jwt = request.getHeader("Authorization");
        jwt = jwt.split(" ")[1];
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer id = (Integer) claims.get("id");
        Integer role = (Integer) claims.get("role");
        Discussion discussion = new Discussion();
        discussion.setCourseId(courseId);
        discussion.setDiscussionContent(discussionsVO.getDiscussion_content());
        discussion.setDiscussionTitle(discussionsVO.getDiscussion_title());
        Date date = new Date();

        // 转换为java.time.Instant
        Instant instant = date.toInstant();
        // 转换为java.time.LocalDateTime
        // 使用默认时区
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        discussion.setCreatedTime(localDateTime);
        discussion.setPosterId(id);//发帖者id
        discussion.setPosterRole(role);//角色2教师，3学生
        Discussion save = discussionRepository.save(discussion);
        Map<String, Object> stringObjectHashMap = new HashMap<>();//返回数据结构
        stringObjectHashMap.put("code",0);
        stringObjectHashMap.put("msg","成功");

        Map<String, Object> stringObjectHashMap1 = new HashMap<>();//返回数据信息
        stringObjectHashMap1.put("discussion_id",save.getDiscussionId());
        stringObjectHashMap.put("data",stringObjectHashMap1);

        return  stringObjectHashMap;

    }

    public Map<String, Object> selectReviews(Integer courseId, Integer discussionId, Integer lastReviewId, Integer num) {
        List<Review> all = reviewRepository.findAll();
        List<Review> collect = all.stream().filter(t -> t.getDiscussionId().equals(discussionId)).sorted(Comparator.comparing(Review::getCreatedTime).reversed()).collect(Collectors.toList());
        /*if (null!=lastReviewId) {
            collect = collect.stream().filter(t -> t.getReviewId().equals(lastReviewId)).collect(Collectors.toList());
        }
        if (null!=num) {
            collect = collect.stream().limit(num).collect(Collectors.toList());
        }*/

        Map<String, Object> stringObjectHashMap = new HashMap<>();//返回数据结构
        stringObjectHashMap.put("code",0);
        stringObjectHashMap.put("msg","成功");
        Map<String, Object> stringObjectHashMap3 = new HashMap<>();

        List<Map<String, Object>> hashMaps = new ArrayList<>();
        for (Review review : collect) {
            Map<String, Object> stringObjectHashMap1 = new HashMap<>();//返回数据信息
            stringObjectHashMap1.put("review_id",review.getDiscussionId());
            stringObjectHashMap1.put("review_content",review.getReviewContent());
            stringObjectHashMap1.put("created_time",review.getCreatedTime());
            Map<String, Object> stringObjectHashMap2 = new HashMap<>();
            if (review.getReviewerRole() == 2) {
                Optional<Teacher> byId = teacherRepository.findById(review.getReviewerId());
                stringObjectHashMap2.put("user_id",byId.get().getId());
                stringObjectHashMap2.put("user_name",byId.get().getName());
                stringObjectHashMap2.put("user_avatar",byId.get().getAvatar());
                stringObjectHashMap2.put("user_role",2);
                stringObjectHashMap1.put("reviewer",stringObjectHashMap2);
            }else if (review.getReviewerRole()==3) {
                Optional<Student> byId = studentRepository.findById(Long.valueOf(review.getReviewerId()));
                stringObjectHashMap2.put("user_id",byId.get().getId());
                stringObjectHashMap2.put("user_name",byId.get().getName());
                stringObjectHashMap2.put("user_avatar",byId.get().getAvatar());
                stringObjectHashMap2.put("user_role",3);
                stringObjectHashMap1.put("reviewer",stringObjectHashMap2);
            }
            hashMaps.add(stringObjectHashMap1);
        }
        stringObjectHashMap3.put("reviews",hashMaps);
        stringObjectHashMap.put("data",stringObjectHashMap3);
        return stringObjectHashMap;



    }

    public Map<String, Object> addReviews(HttpServletRequest request, Integer courseId, Integer discussionId, ReviewsVO reviewsVO) {
        String jwt = request.getHeader("Authorization");
        jwt = jwt.split(" ")[1];
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer id = (Integer) claims.get("id");
        Integer role = (Integer) claims.get("role");
        Review reviews = new Review();
        reviews.setDiscussionId(discussionId);
        reviews.setReviewContent(reviewsVO.getReview_content());
        reviews.setReviewerId(id);
        reviews.setReviewerRole(role);
        Date date = new Date();

        // 转换为java.time.Instant
        Instant instant = date.toInstant();
        // 转换为java.time.LocalDateTime
        // 使用默认时区
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        reviews.setCreatedTime(localDateTime);
        Review save = reviewRepository.save(reviews);
        Map<String, Object> stringObjectHashMap = new HashMap<>();//返回数据结构
        stringObjectHashMap.put("code",0);
        stringObjectHashMap.put("msg","成功");
        Map<String, Object> stringObjectHashMap1 = new HashMap<>();//返回数据信息
        stringObjectHashMap1.put("review_id",save.getReviewId());
        stringObjectHashMap.put("data",stringObjectHashMap1);
        return  stringObjectHashMap;


    }

    public Map<String, Object> deleteReviewsOrDiscussion(Long courseId, Long discussionId, Boolean isReply) {
        if (null!=isReply&&isReply) {
            reviewRepository.deleteById(discussionId);
        }else {
            discussionRepository.deleteById(discussionId);
        }
        Map<String, Object> stringObjectHashMap = new HashMap<>();//返回数据结构
        stringObjectHashMap.put("code",0);
        stringObjectHashMap.put("msg","成功");
        Map<String, Object> stringObjectHashMap1 = new HashMap<>();//返回数据信息
        stringObjectHashMap1.put("course_id",courseId);
        stringObjectHashMap1.put("discussion_id",discussionId);
        stringObjectHashMap1.put("is_reply",isReply);
        stringObjectHashMap.put("data",stringObjectHashMap1);
        return  stringObjectHashMap;
    }
}
