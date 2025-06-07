package com.zch1001.pi.controller;

import com.zch1001.pi.entity.VO.CourseVO;
import com.zch1001.pi.model.StudentCoursesResponse;
import com.zch1001.pi.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
public class StudentController {

    @Autowired
    private StudentCourseService studentCourseService;

    @GetMapping("/students/{student_id}/courses")
    public ResponseEntity<StudentCoursesResponse> getStudentCourses(@PathVariable("student_id") Integer studentId) {
        StudentCoursesResponse response = studentCourseService.getCoursesByStudentId(studentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/courses/{course_id}/statistics/students/{student_id}")//查看课程的某位学生的统计信息
    public Map<String,Object> selectStudentMessage(@PathVariable("course_id") Long  course_id, @PathVariable("student_id") Integer  student_id) {
        Map<String,Object> response = studentCourseService.selectStudentMessage(course_id,student_id);
        return response;
    }

    @GetMapping("/courses/{course_id}/students/{student_id}/statistics")//单个学生数据统计,
    public Map<String,Object> selectStudentMessageOne(@PathVariable("course_id") Long course_id, @PathVariable("student_id") Integer  student_id,String from_date,String to_date) {
        Map<String,Object> response = studentCourseService.selectStudentMessageOne(
                course_id,student_id,from_date,to_date);
        return response;
    }


}
