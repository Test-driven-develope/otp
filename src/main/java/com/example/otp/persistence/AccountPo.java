package com.example.otp.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "account")
public class AccountPo {
    @Id
    private int id;
    private String phoneNumber;
    private boolean valid;
}