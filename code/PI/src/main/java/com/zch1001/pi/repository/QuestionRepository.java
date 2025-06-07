package com.zch1001.pi.repository;

import com.zch1001.pi.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findById(int id);
    List<Question> findByBankId(int bankId);
}