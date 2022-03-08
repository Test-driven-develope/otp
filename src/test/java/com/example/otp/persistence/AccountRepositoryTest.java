package com.example.otp.persistence;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import com.example.otp.DBTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AccountRepositoryTest extends DBTestBase {
    @Autowired
    private AccountRepository accountRepository;
    
    @Test
    void should_can_save_account_and_find_by_phone_number() {
        AccountPo accountPo = AccountPo.builder().phoneNumber("15342349111").valid(true).build();
    
        AccountPo result = accountRepository.save(accountPo);
    
        accountRepository.flush();
        
        Optional<AccountPo> optionalAccountPo = accountRepository.findByPhoneNumber(result.getPhoneNumber());
    
        assertTrue(optionalAccountPo.isPresent());
        assertTrue(optionalAccountPo.get().getId() > 0);
        assertEquals("15342349111", optionalAccountPo.get().getPhoneNumber());
        assertTrue(optionalAccountPo.get().isValid());
    }
}