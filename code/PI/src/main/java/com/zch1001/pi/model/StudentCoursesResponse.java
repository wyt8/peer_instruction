package com.zch1001.pi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zch1001.pi.entity.Course;
import com.zch1001.pi.entity.Teacher;

import java.util.List;

public class StudentCoursesResponse {

    @JsonProperty("code")
    private int code;  // 状态码

    @JsonProperty("msg")
    private String msg;  // 消息

    @JsonProperty("data")
    private CourseDataList data;  // 数据对象，包含课程列表

    // 构造函数
    public StudentCoursesResponse(int code, String msg, List<CourseData> courses) {
        this.code = code;
        this.msg = msg;
        this.data = new CourseDataList(courses);
    }




    //封装课程列表
    public static class CourseDataList {

        @JsonProperty("courses")
        private List<CourseData> courses;

        public CourseDataList(List<CourseData> courses) {
            this.courses = courses;
        }

        public List<CourseData> getCourses() {
            return courses;
        }

        public void setCourses(List<CourseData> courses) {
            this.courses = courses;
        }
    }


    public static class CourseData {

        @JsonProperty("course_id")
        private Long courseId;

        @JsonProperty("course_name")
        private String courseName;

        @JsonProperty("course_image_url")
        private String courseImageUrl;

        @JsonProperty("join_time")
        private String joinTime;

        @JsonProperty("teacher")
        private TeacherInfo teacher;

        // 构造函数
        public CourseData(Course course, String joinTime, Teacher teacher) {
            this.courseId = course.getCourseId();
            this.courseName = course.getCourseName();
            this.courseImageUrl = course.getCourseImageUrl();
            this.joinTime = joinTime;
            this.teacher = new TeacherInfo(teacher);
        }

        // Getters and Setters
        public Long getCourseId() {
            return courseId;
        }

        public void setCourseId(Long courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseImageUrl() {
            return courseImageUrl;
        }

        public void setCourseImageUrl(String courseImageUrl) {
            this.courseImageUrl = courseImageUrl;
        }

        public String getJoinTime() {
            return joinTime;
        }

        public void setJoinTime(String joinTime) {
            this.joinTime = joinTime;
        }

        public TeacherInfo getTeacher() {
            return teacher;
        }

        public void setTeacher(TeacherInfo teacher) {
            this.teacher = teacher;
        }
    }


    public static class TeacherInfo {

        @JsonProperty("teacher_id")
        private Long teacherId;

        @JsonProperty("teacher_name")
        private String teacherName;

        @JsonProperty("teacher_avatar")
        private String teacherAvatar;

        // 构造函数
        public TeacherInfo(Teacher teacher) {
            this.teacherId = (long)teacher.getId();
            this.teacherName = teacher.getName();
            this.teacherAvatar = teacher.getAvatar();
        }

        // Getters and Setters
        public Long getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(Long teacherId) {
            this.teacherId = teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getTeacherAvatar() {
            return teacherAvatar;
        }

        public void setTeacherAvatar(String teacherAvatar) {
            this.teacherAvatar = teacherAvatar;
        }
    }
}
