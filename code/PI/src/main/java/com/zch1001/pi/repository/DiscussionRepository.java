package com.zch1001.pi.repository;

import com.zch1001.pi.entity.Discussion;
import com.zch1001.pi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
}