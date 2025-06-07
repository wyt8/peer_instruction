package com.zch1001.pi.repository;

import com.zch1001.pi.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<Class, Integer> {
    Class findByClassId(int id);
    List<Class> findByCourseId(Long CourseId);
}