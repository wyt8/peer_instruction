package com.zch1001.pi.controller;

import com.zch1001.pi.model.*;
import com.zch1001.pi.service.CourseService;
import com.zch1001.pi.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @PostMapping("/courses/{course_id}/notices")
    public PublishNoticeResponse publishNotice(@PathVariable Integer course_id, @RequestBody PublishNoticeRequest publishNoticeRequest) {
        return noticeService.publishNotice(course_id, publishNoticeRequest);
    }

    @GetMapping("/students/{student_id}/notices")
    public QueryNoticeResponse queryNotice(@PathVariable Integer student_id) {
        return noticeService.queryNotice(student_id);
    }

    @PostMapping("/students/{student_id}/notices/{notice_id}/read")
    public ReadNoticeResponse readNotice(@PathVariable Integer student_id, @PathVariable Integer notice_id, @RequestBody ReadNoticeRequest readNoticeRequest) {
        return noticeService.readNotice(student_id, notice_id, readNoticeRequest);
    }
}
