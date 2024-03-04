package com.fastcampuspay.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {

    @Getter private final String memberMoneyId;

    @Getter private final String membershipId;

    @Getter private final int balance; // 0: 증액, 1: 감액

//    @Getter private final int linkedBankAccount;


    public static MemberMoney generateMemberMoney(MemberMoneyId memberMoneyId, MembershipId membershipId, Balance balance) {
        return new MemberMoney(
                memberMoneyId.getMemberMoneyId(),
                membershipId.getMembershipId(),
                balance.getBalance()
        );
    }

    @Value
    public static class MemberMoneyId {
        public MemberMoneyId(String value) {
            this.memberMoneyId = value;
        }

        String memberMoneyId;
    }

    @Value
    public static class MembershipId {
        public MembershipId(String value) {
            this.membershipId = value;
        }

        String membershipId;
    }

    @Value
    public static class Balance {
        public Balance(int value) {
            this.balance = value;
        }

        int balance;
    }
}
