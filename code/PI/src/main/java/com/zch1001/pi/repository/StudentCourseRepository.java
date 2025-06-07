package com.zch1001.pi.repository;

import com.zch1001.pi.entity.StudentCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentCourseRepository extends JpaRepository<StudentCourses, Long> {
    List<StudentCourses> findByStudentId(Integer studentId);  // 根据学生ID查找加入的课程记录
    List<StudentCourses> findByStudentIdAndCourseId(Integer studentId, Long courseId);
    List<StudentCourses> findByCourseId(Long courseId);
}
