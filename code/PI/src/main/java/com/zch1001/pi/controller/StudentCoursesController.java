package com.zch1001.pi.controller;

import com.zch1001.pi.entity.VO.CourseVO;
import com.zch1001.pi.model.StudentCoursesResponse;
import com.zch1001.pi.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unbescape.json.JsonEscapeLevel;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class StudentCoursesController {
    @Autowired
    private StudentCourseService studentCourseService;

    @PostMapping("/students/{student_id}/courses")
    public Map<String,Object> addStudentCourses(@PathVariable("student_id") Integer studentId, @RequestBody CourseVO courseVO) {
        Map<String,Object> response = studentCourseService.addStudentCourses(studentId,courseVO);
        return response;
    }


}
