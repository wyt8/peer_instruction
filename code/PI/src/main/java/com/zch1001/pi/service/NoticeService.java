package com.zch1001.pi.service;

import com.zch1001.pi.entity.*;
import com.zch1001.pi.model.*;
import com.zch1001.pi.repository.CourseRepository;
import com.zch1001.pi.repository.NoticeRepository;
import com.zch1001.pi.repository.StudentCourseRepository;
import com.zch1001.pi.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentCourseRepository studentCourseRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    public PublishNoticeResponse publishNotice(Integer courseId, PublishNoticeRequest publishNoticeRequest) {
        PublishNoticeResponse publishNoticeResponse = new PublishNoticeResponse();
        PublishNoticeRequest.NoticeInfo noticeInfo = publishNoticeRequest.getNoticeInfo();
        Notice notice = new Notice();
        noticeRepository.save(notice);
        notice.setNoticeContent(noticeInfo.getContent());
        notice.setNoticeTime(LocalDateTime.now());
        notice.setCourseId(courseId);
        noticeRepository.save(notice);
        PublishNoticeResponse.Data data = publishNoticeResponse.new Data();
        data.setNoticeId(notice.getNoticeId());
        data.setContent(noticeInfo.getContent());
        data.setSendTime(LocalDateTime.now());
        publishNoticeResponse.setCode(0);
        publishNoticeResponse.setMsg("Success!");
        publishNoticeResponse.setData(data);
        return publishNoticeResponse;
    }

    public QueryNoticeResponse queryNotice(Integer studentId) {
        QueryNoticeResponse queryNoticeResponse = new QueryNoticeResponse();
        queryNoticeResponse.setCode(0);
        queryNoticeResponse.setMsg("Success!");
        QueryNoticeResponse.Data data = queryNoticeResponse.new Data();
        List<QueryNoticeResponse.NoticeInfo> noticeInfoList = new ArrayList<>();
        List<StudentCourses> studentCoursesList = studentCourseRepository.findByStudentId(studentId);
        for (StudentCourses studentCourses : studentCoursesList) {
            QueryNoticeResponse.CourseInfo courseInfo = queryNoticeResponse.new CourseInfo();
            Long courseId = studentCourses.getCourseId();
            Course course = courseRepository.findById(courseId).get();
            courseInfo.setCourseId(courseId);
            courseInfo.setCourseName(course.getCourseName());
            courseInfo.setCourseImageUrl(course.getCourseImageUrl());
            courseInfo.setJoinTime(course.getCreatedTime());
            QueryNoticeResponse.TeacherInfo teacherInfo = queryNoticeResponse.new TeacherInfo();
            Teacher teacher = teacherRepository.findById(course.getTeacherId()).get();
            teacherInfo.setTeacherId(teacher.getId());
            teacherInfo.setTeacherName(teacher.getName());
            teacherInfo.setTeacherAvatar(teacher.getAvatar());
            courseInfo.setTeacher(teacherInfo);
            List<Notice> noticeList = noticeRepository.findByCourseId(courseId);
            for (Notice notice : noticeList) {
                QueryNoticeResponse.NoticeInfo noticeInfo = queryNoticeResponse.new NoticeInfo();
                noticeInfo.setNoticeId(notice.getNoticeId());
                noticeInfo.setContent(notice.getNoticeContent());
                noticeInfo.setSendTime(notice.getNoticeTime());
                List<Integer> studentList = notice.getStudent();
                if (studentList.contains(studentId)) noticeInfo.setReceived(true);
                else noticeInfo.setReceived(false);
                noticeInfo.setCourseInfo(courseInfo);
                noticeInfoList.add(noticeInfo);
            }
        }
        data.setNoticeInfoList(noticeInfoList);
        queryNoticeResponse.setData(data);
        return queryNoticeResponse;
    }

    public ReadNoticeResponse readNotice(Integer StudentId, Integer NoticeId, ReadNoticeRequest readNoticeRequest) {
        ReadNoticeResponse readNoticeResponse = new ReadNoticeResponse();
        readNoticeResponse.setCode(0);
        readNoticeResponse.setMsg("Success!");
        ReadNoticeResponse.Data data = readNoticeResponse.new Data();
        Notice notice = noticeRepository.findById(NoticeId).get();
        data.setSuccess(true);
        data.setContent(notice.getNoticeContent());
        notice.getStudent().add(StudentId);
        noticeRepository.save(notice);
        readNoticeResponse.setData(data);
        return readNoticeResponse;
    }

}
