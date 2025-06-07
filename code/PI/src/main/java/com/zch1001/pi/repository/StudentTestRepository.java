package com.zch1001.pi.repository;

import com.zch1001.pi.entity.Student;
import com.zch1001.pi.entity.StudentTest;
import com.zch1001.pi.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentTestRepository extends JpaRepository<StudentTest, Long> {
    List<StudentTest> findByStudentIdAndTestId(Integer studentId, Integer testId);
    List<StudentTest> findByStatus(Integer status);
    List<StudentTest> findByStudentId(Integer studentId);
    List<StudentTest> findByStudentIdAndClassId(Integer studentId, Integer classId);

    List<StudentTest> findByClassIdAndStatus(Integer classId, Integer status);

    List<StudentTest> findByClassId(Integer classId);

    StudentTest findByStudentIdAndTestIdAndTestType(Long studentId, int exerciseId, int TestType);

}