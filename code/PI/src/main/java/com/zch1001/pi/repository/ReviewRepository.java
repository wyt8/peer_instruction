package com.zch1001.pi.repository;

import com.zch1001.pi.entity.Discussion;
import com.zch1001.pi.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}