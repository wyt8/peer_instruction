package com.zch1001.pi.repository;

import com.zch1001.pi.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    Optional<Notice> findById(Integer id);
    List<Notice> findByCourseId(Long id);
}