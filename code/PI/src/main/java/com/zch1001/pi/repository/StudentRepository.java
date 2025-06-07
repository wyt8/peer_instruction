package com.zch1001.pi.repository;

import com.zch1001.pi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findById(Long id);
    Optional<Student> findByEmail(String email);
}