package com.zch1001.pi.service;

import com.zch1001.pi.entity.*;
import com.zch1001.pi.entity.VO.AddCourseVO;
import com.zch1001.pi.entity.VO.AddExistCourse;
import com.zch1001.pi.entity.VO.StudentIdVO;
import com.zch1001.pi.model.PublishNoticeRequest;
import com.zch1001.pi.model.PublishNoticeResponse;
import com.zch1001.pi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BankRepository bankRepository;

    public Map<String, Object> addCourse(Long teacherId, AddCourseVO addCourseVO) {
        Course course = new Course();
        course.setCourseName(addCourseVO.getCourse_name());
        course.setCourseImageUrl(addCourseVO.getCourse_image_url());
        course.setTeacherId(teacherId);
        Date date = new Date();

        // 转换为java.time.Instant
        Instant instant = date.toInstant();
        // 转换为java.time.LocalDateTime
        // 使用默认时区
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        course.setCreatedTime(localDateTime);
        Course save = courseRepository.save(course);

        Optional<Teacher> byId = teacherRepository.findById(teacherId);

// 创建题库对象
        Bank bank = new Bank();
        bank.setCourseId(save.getCourseId()); // 设置课程ID
        bank.setBankName(save.getCourseName()); // 题库名称与课程名称相同
        bank.setQuestionNumber(0); // 初始题目数量为0
        bank.setUpdater(byId.get().getName()); // 设置修改者
        bank.setUpdatedTime(save.getCreatedTime()); // 设置修改时间为课程创建时间

// 保存题库
        Bank save2 = bankRepository.save(bank);

        Map<String, Object> stringObjectHashMap = new HashMap<>();//返回数据结构
        stringObjectHashMap.put("code", 0);
        stringObjectHashMap.put("msg", "成功");
        Map<String, Object> stringObjectHashMap1 = new HashMap<>();//返回数据信息
        stringObjectHashMap1.put("course_id", save.getCourseId());
        stringObjectHashMap1.put("course_image_url", save.getCourseImageUrl());
        stringObjectHashMap1.put("created_time", save.getCreatedTime());
        stringObjectHashMap1.put("course_name", save.getCourseName());
        Map<String, Object> stringObjectHashMap3 = new HashMap<>();//返回题库信息
        stringObjectHashMap3.put("bank_id", save2.getBankId());
        stringObjectHashMap3.put("name", save2.getBankName());
        stringObjectHashMap3.put("updater", save2.getUpdater());
        stringObjectHashMap3.put("updated_time", save2.getUpdatedTime());
        stringObjectHashMap1.put("bank", stringObjectHashMap3);
        stringObjectHashMap.put("data", stringObjectHashMap1);
        return stringObjectHashMap;

    }

    public Map<String,Object> addexistcourse(Long courseId, AddExistCourse addExistCourse) {
        Optional<Course> courseOptional = courseRepository.findById((long)courseId);
        Map<String, Object> response = new HashMap<>();

        if (!courseOptional.isPresent()) {
            response.put("code", 404);
            response.put("msg", "Course not found");
            return response;
        }
        Course course = courseOptional.get();
        course.setTeacherId(addExistCourse.getTeacher_id());
        courseRepository.save(course);
        response.put("code", 0);
        response.put("msg","okk");
        Map<String, Object> okk = new HashMap<>();
        okk.put("course_id", course.getCourseId());
        okk.put("course_name", course.getCourseName());
        okk.put("created_time", course.getCreatedTime());
        okk.put("course_image_url", course.getCourseImageUrl());
        Optional<Teacher> byId = teacherRepository.findById(addExistCourse.getTeacher_id());
        Map<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("teacher_id", byId.get().getId());
        stringObjectHashMap.put("teacher_name", byId.get().getName());
        stringObjectHashMap.put("teacher_avatar", byId.get().getAvatar());
        okk.put("teacher", stringObjectHashMap);
        response.put("data", okk);
        return response;
    }

    public Map<String, Object> addStudentCourse(Long courseId, StudentIdVO studentIdVO) {
        StudentCourses studentCourses = new StudentCourses();
        studentCourses.setCourseId(courseId);
        studentCourses.setStudentId(studentIdVO.getStudent_id());
        Date date = new Date();
        // 转换为java.time.Instant
        Instant instant = date.toInstant();
        // 转换为java.time.LocalDateTime
        // 使用默认时区
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        studentCourses.setCreatedTime(localDateTime);
        studentCourseRepository.save(studentCourses);
        Map<String, Object> stringObjectHashMap = new HashMap<>();//返回数据结构
        stringObjectHashMap.put("code", 0);
        stringObjectHashMap.put("msg", "成功");
        Map<String, Object> stringObjectHashMap1 = new HashMap<>();//返回数据信息
        Optional<Student> byId = studentRepository.findById(studentIdVO.getStudent_id());
        stringObjectHashMap1.put("course_id", courseId.toString());
        stringObjectHashMap1.put("student_id", studentIdVO.getStudent_id().toString());
        stringObjectHashMap1.put("attend_time", localDateTime);
        stringObjectHashMap1.put("student_name", byId.get().getName());
        Optional<Course> byId1 = courseRepository.findById(courseId);
        Optional<Teacher> byId2 = teacherRepository.findById(byId1.get().getTeacherId());
        Map<String, Object> stringObjectHashMap2 = new HashMap<>();//返回数据信息
        stringObjectHashMap2.put("teacher_id", byId2.get().getId());
        stringObjectHashMap2.put("teacher_name", byId2.get().getName());
        stringObjectHashMap2.put("teacher_avatar", byId2.get().getAvatar());
        stringObjectHashMap1.put("teacher", stringObjectHashMap2);
        stringObjectHashMap.put("data", stringObjectHashMap1);
        return stringObjectHashMap;
    }

    public Map<String, Object> deleteStudentCourse(Long courseId, Long studentId) {
        List<StudentCourses> all = studentCourseRepository.findAll();
        List<StudentCourses> collect = all.stream().filter(t -> t.getStudentId().toString().equals(String.valueOf(studentId)) && t.getCourseId().toString().equals(courseId.toString())).collect(Collectors.toList());
        studentCourseRepository.deleteAll(collect);
        Map<String, Object> stringObjectHashMap = new HashMap<>();//返回数据结构
        stringObjectHashMap.put("code", 0);
        stringObjectHashMap.put("msg", "成功");
        Map<String, Object> stringObjectHashMap1 = new HashMap<>();//返回数据信息
        Optional<Student> byId = studentRepository.findById(studentId);
        Optional<Course> byId1 = courseRepository.findById(courseId);
        stringObjectHashMap1.put("course_id", courseId.toString());
        stringObjectHashMap1.put("student_id", studentId.toString());
        stringObjectHashMap1.put("student_name", byId.get().getName());
        stringObjectHashMap1.put("course_name", byId1.get().getCourseName());
        stringObjectHashMap.put("data", stringObjectHashMap1);
        return   stringObjectHashMap;
    }

    public Map<String, Object> selectClass(String courseName, String courseId) {
        List<Course> all = courseRepository.findAll();
        if (null!=courseId&&!courseId.equals("")) {
            all = all.stream().filter(t -> t.getCourseId().toString().equals(courseId)).sorted(Comparator.comparing(Course::getCreatedTime).reversed()).collect(Collectors.toList());
        }
        if (null!=courseName&&!courseName.equals("")) {
            all = all.stream().filter(t -> t.getCourseName().equals(courseName)).sorted(Comparator.comparing(Course::getCreatedTime).reversed()).collect(Collectors.toList());
        }
        Map<String, Object> stringObjectHashMap = new HashMap<>();//返回数据结构
        stringObjectHashMap.put("code",0);
        stringObjectHashMap.put("msg","成功");
        List<HashMap> hashMaps = new ArrayList<>();
        for (Course course : all) {
            HashMap<String, Object> stringObjectHashMap1 = new HashMap<>();
            stringObjectHashMap1.put("course_id",course.getCourseId());
            stringObjectHashMap1.put("course_name",course.getCourseName());
            stringObjectHashMap1.put("course_image_url",course.getCourseImageUrl());
            stringObjectHashMap1.put("join_time",course.getCreatedTime());
            HashMap<String, Object> stringObjectHashMap2 = new HashMap<>();
            Optional<Teacher> byId = teacherRepository.findById(course.getTeacherId());
            stringObjectHashMap2.put("teacher_id",byId.get().getId());
            stringObjectHashMap2.put("teacher_name",byId.get().getName());
            stringObjectHashMap2.put("teacher_avatar",byId.get().getAvatar());
            stringObjectHashMap1.put("teacher",stringObjectHashMap2);
            hashMaps.add(stringObjectHashMap1);
        }
        Map<String, Object> stringObjectHashMap1 = new HashMap<>();
        stringObjectHashMap1.put("courses",hashMaps);
        stringObjectHashMap.put("data",stringObjectHashMap1);
        return stringObjectHashMap;

    }

    public Map<String, Object> getTeacherCourseList(String teacherId) {
        // 获取该教师的所有课程
        List<Course> allCourse = courseRepository.findAll();
        List<Course> allCourses=allCourse.stream().filter(t -> t.getTeacherId().toString().equals(teacherId)).collect(Collectors.toList());

        // 初始化返回数据结构
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0); // 成功
        response.put("msg", "成功");

        // 封装课程列表
        List<Map<String, Object>> courseList = new ArrayList<>();
        for (Course course : allCourses) {
            Map<String, Object> courseInfo = new HashMap<>();
            courseInfo.put("course_id", course.getCourseId());
            courseInfo.put("course_name", course.getCourseName());
            courseInfo.put("course_image_url", course.getCourseImageUrl());

            courseList.add(courseInfo);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("courses", courseList);
        response.put("data", data);

        return response;
    }

    public Map<String, Object> getCourseStudentList(Long courseId, Long studentId) {
        // 查询所有学生课程记录
        List<StudentCourses> all = studentCourseRepository.findAll();
        // 过滤出指定课程的所有学生记录
        List<StudentCourses> students = all.stream()
                .filter(t -> t.getCourseId().equals(courseId))
                .collect(Collectors.toList());

        // 如果指定了学生ID，则进一步过滤
        if (studentId != null) {
            students = students.stream()
                    .filter(t -> t.getStudentId().equals(studentId))
                    .collect(Collectors.toList());
        }
        // 初始化返回数据结构
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0); // 成功
        response.put("msg", "成功");

        // 封装学生列表
        List<Map<String, Object>> studentList = new ArrayList<>();
        for (StudentCourses studentCourse : students) {
            // 根据学生ID从学生表查询详细信息
            Optional<Student> studentOptional = studentRepository.findById(studentCourse.getStudentId());
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                // 构建学生信息
                Map<String, Object> studentInfo = new HashMap<>();
                studentInfo.put("student_id", student.getId());
                studentInfo.put("student_name", student.getName());
                studentInfo.put("student_avatar", student.getAvatar());


                // 添加到学生列表
                studentList.add(studentInfo);
            }
        }

        // 将学生列表加入返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("students", studentList);
        response.put("data", data);

        return response;
    }


}
