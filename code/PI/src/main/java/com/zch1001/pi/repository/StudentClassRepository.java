package com.zch1001.pi.repository;

import com.zch1001.pi.entity.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentClassRepository  extends JpaRepository<StudentClass, Long> {
    List<StudentClass> findByStudentId(Integer studentId);
    List<StudentClass> findByClassId(Integer classId);
    Optional<StudentClass> findByStudentIdAndClassId(Integer studentId, Integer classId);
}
