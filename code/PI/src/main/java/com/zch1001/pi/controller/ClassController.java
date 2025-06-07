package com.zch1001.pi.controller;

import com.zch1001.pi.model.*;
import com.zch1001.pi.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
public class ClassController {
    @Autowired
    private ClassService classService;

    @GetMapping("/students/{student_id}/courses/{course_id}/classes")
    public QueryClassResponse queryClass(@PathVariable int student_id, @PathVariable Long course_id) {
        return classService.queryClass(student_id, course_id);
    }

    @PostMapping("/students/{student_id}/courses/{course_id}/classes/{class_id}")
    public JoinClassResponse joinClass(@PathVariable int student_id, @PathVariable int course_id, @PathVariable int class_id, @RequestBody JoinClassRequest joinClassRequest) {
        return classService.joinClass(student_id, course_id, class_id, joinClassRequest);
    }

    @PostMapping("/students/{student_id}/exercises/{exercise_id}")
    public SubmitQuestionResponse submitQuestion(@PathVariable Long student_id, @PathVariable int exercise_id, @RequestBody SubmitQuestionRequest submitQuestionRequest) {
        return classService.submitQuestion(student_id, exercise_id, submitQuestionRequest);
    }

    @GetMapping("/students/{student_id}/courses/{course_id}/classes/{class_id}/exercises")
    public GetQuestionResponse getQuestion(@PathVariable int student_id, @PathVariable int course_id, @PathVariable int class_id) {
        return classService.getQuestion(student_id, course_id, class_id);
    }

    @GetMapping("/students/{student_id}/courses/{course_id}/classes/{class_id}")
    public SignInResponse signIn(@PathVariable int student_id, @PathVariable int course_id, @PathVariable int class_id) {
        return classService.signIn(student_id, course_id, class_id);
    }

    @PostMapping("/class/create")
    public CreateClassResponse createClass(@RequestBody CreateClassRequest createClassRequest) {
        return classService.createClass(createClassRequest);
    }

    @GetMapping("/class/list")
    public TeacherQueryClassResponse teacherQueryClass(@RequestParam("course_id") Long course_id) {
        return classService.teacherQueryClass(course_id);
    }

    @GetMapping("/class/questions")
    public TeacherQueryQuestionResponse teacherQueryQuestion(@RequestParam("course_id") Long course_id) {
        return classService.teacherQueryQuestion(course_id);
    }

    @PostMapping("/class/exercise/add")
    public AddTestResponse addTest(@RequestBody AddTestRequest addTestRequest) {
        return classService.addTest(addTestRequest);
    }

    @PostMapping("/class/publish")
    public PublishClassResponse publishClass(@RequestBody PublishClassRequest publishClassRequest) {
        return classService.publishClass(publishClassRequest);
    }

    @PostMapping("/class/sign/publish")
    public PublishSignResponse publishSign(@RequestBody PublishSignRequest publishSignRequest) {
        return classService.publishSign(publishSignRequest);
    }

    @PostMapping("/class/exercise/publish")
    public PublishTestResponse publishTest(@RequestBody PublishTestRequest publishTestRequest) {
        return classService.publishTest(publishTestRequest);
    }

    @GetMapping("/class/detail")
    ClassDetailResponse classDetail(@RequestParam("class_id") Integer class_id) {
        return classService.classDetail(class_id);
    }

    @GetMapping("/class/students")
    QueryStudentResponse queryStudent(@RequestParam("class_id") Integer class_id) {
        return classService.queryStudent(class_id);
    }

    @GetMapping("/class/review")
    ReviewClassResponse reviewClass(@RequestParam("class_id") Integer class_id) {
        return classService.reviewClass(class_id);
    }

    @GetMapping("/class/end")
    EndClassResponse endClass(@RequestParam("class_id") Integer class_id) {
        return classService.endClass(class_id);
    }

    @PostMapping("/students/{student_id}/classes/{class_id}")//课堂签到
    public Map<String,Object> addClassSignIn(@PathVariable("student_id") Integer studentId, @PathVariable("class_id") Integer classId) {
        Map<String,Object> response = classService.addClassSignIn(studentId,classId);
        return response;
    }

}
