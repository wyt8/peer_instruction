package com.zch1001.pi.repository;

import com.zch1001.pi.entity.StudentTest;
import com.zch1001.pi.entity.Teacher;
import com.zch1001.pi.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {
    Optional<Test> findByTestId(Integer id);
    List<Test> findByClassId(Integer classId);
}
