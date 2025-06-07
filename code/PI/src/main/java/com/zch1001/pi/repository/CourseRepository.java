package com.zch1001.pi.repository;

import com.zch1001.pi.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTeacherId(int teacherId);

    // JpaRepository 已经提供了 findById 方法，可以直接使用，无需重新定义
}
