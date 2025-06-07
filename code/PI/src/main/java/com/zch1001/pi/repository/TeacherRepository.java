package com.zch1001.pi.repository;

import com.zch1001.pi.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Optional<Teacher> findById(Long id);
    Optional<Teacher> findByEmail(String email);
}