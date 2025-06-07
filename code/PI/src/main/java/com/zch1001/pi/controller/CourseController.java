package com.zch1001.pi.controller;



import com.zch1001.pi.entity.VO.AddCourseVO;
import com.zch1001.pi.entity.VO.AddExistCourse;
import com.zch1001.pi.entity.VO.StudentIdVO;
import com.zch1001.pi.model.PublishNoticeRequest;
import com.zch1001.pi.model.PublishNoticeResponse;
import com.zch1001.pi.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/courses/teachers/{teacher_id}")//教师创建全新课程
    public Map<String,Object> addCourse(@PathVariable("teacher_id") Long  teacher_id,@RequestBody  AddCourseVO addCourseVO) {
        Map<String,Object> response = courseService.addCourse(teacher_id,addCourseVO);
        return response;
    }

    @PostMapping("/courses/{course_id}/attend")//教师加入已有课程
    public Map<String,Object> addexistcourse(@PathVariable("course_id") Long course_id ,@RequestBody AddExistCourse addExistCourse) {
        Map <String,Object> response=courseService.addexistcourse(course_id,addExistCourse);
        return  response;
    }

    @PostMapping("/courses/{course_id}/students")//手动加入学生
    public Map<String,Object> addStudentCourse(@PathVariable("course_id") Long  course_id,@RequestBody StudentIdVO studentIdVO) {
        Map<String,Object> response = courseService.addStudentCourse(course_id,studentIdVO);
        return response;
    }

    @DeleteMapping("/courses/{course_id}/students/{student_id}")//删除学生
    public Map<String,Object> deleteStudentCourse(@PathVariable("course_id") Long  course_id,@PathVariable("student_id") Long  student_id) {
        Map<String,Object> response = courseService.deleteStudentCourse(course_id,student_id);
        return response;
    }


    @GetMapping("/courses")//查询课程
    public Map<String,Object> selectClass(String course_name,String course_id) {
        Map<String,Object> response = courseService.selectClass(course_name,course_id);
        return response;
    }
    @GetMapping("/teachers/courses")//查询教师的课程列表
    public Map<String,Object> getTeacherCourseList(String teacher_id) {
        Map<String,Object> response = courseService.getTeacherCourseList(teacher_id);
        return response;
    }

    @GetMapping("/courses/students")//查看课程学生列表
    public Map<String,Object> getCourseStudentList(Long course_id, Long student_id) {
        Map<String,Object> response = courseService.getCourseStudentList(course_id,student_id);
        return response;
    }

}
