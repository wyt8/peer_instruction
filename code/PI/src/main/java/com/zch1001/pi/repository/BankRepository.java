package com.zch1001.pi.repository;

import com.zch1001.pi.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Integer> {
    Bank findById(int id);
    Bank findByCourseId(Long courseId);
}