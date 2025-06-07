package com.zch1001.pi.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.zch1001.pi.entity.VerificationCode;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByEmail(String email);

    @Modifying
    @Transactional
    void deleteByEmail(String email);
}

