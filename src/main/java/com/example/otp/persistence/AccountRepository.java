package com.example.otp.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountPo, Integer> {
    Optional<AccountPo> findByPhoneNumber(String phoneNumber);
}