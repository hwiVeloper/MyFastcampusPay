package com.fastcampuspay.money.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "member_money")
@Data
//@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberMoneyJpaEntity {
    @Id
    @GeneratedValue
    private String membershipId;

    private int balance;

    public MemberMoneyJpaEntity(String membershipId, int balance) {
        this.membershipId = membershipId;
        this.balance = balance;
    }
}
