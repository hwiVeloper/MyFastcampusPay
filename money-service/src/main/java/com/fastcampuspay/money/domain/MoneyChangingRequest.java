package com.fastcampuspay.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyChangingRequest {
    @Getter private final String moneyChangingRequestId;

    // 어떤 고객의 증액/감액 요청을 했는지 멤버 정보
    @Getter private final String targetMembershipId;

    // 증액인지 감액인지
    @Getter private final int changingType; // 0: 증액, 1: 감액
    enum ChangingType {
        INCREASING,
        DECREASING
    }

    // 증액 또는 감액 요청의 금액
    @Getter private final int changingMoneyAmount;

    // 머니 변액 요청에 대한 상태
    @Getter private final int changingMoneyStatus;
    enum ChangingMoneyStatus {
        REQUESTED,
        SUCCEEDED,
        FAILED,
        CANCELLED
    }

    @Getter private final UUID uuid;

    @Getter private final Date createdAt;

    public static MoneyChangingRequest generateMoneyChangingRequest(
            MoneyChangingRequestId moneyChangingRequestId,
            TargetMembershipId targetMembershipId,
            MoneyChangingType moneyChangingType,
            ChangingMoneyAmount changingMoneyAmount,
            MoneyChangingStatus moneyChangingStatus,
            Uuid uuid
    ) {
        return new MoneyChangingRequest(
                moneyChangingRequestId.getMoneyChangingRequestId(),
                targetMembershipId.getTargetMembershipId(),
                moneyChangingType.getChangingType(),
                changingMoneyAmount.getChangingMoneyAmount(),
                moneyChangingStatus.getChangingMoneyStatus(),
                uuid.getUuid(),
                new Date()
        );
    }

    @Value
    public static class MoneyChangingRequestId {
        public MoneyChangingRequestId(String value) {
            this.moneyChangingRequestId = value;
        }

        String moneyChangingRequestId;
    }

    @Value
    public static class TargetMembershipId {
        public TargetMembershipId(String value) {
            this.targetMembershipId = value;
        }

        String targetMembershipId;
    }

    @Value
    public static class MoneyChangingType {
        public MoneyChangingType(int value) {
            this.changingType = value;
        }

        int changingType;
    }

    @Value
    public static class ChangingMoneyAmount {
        public ChangingMoneyAmount(int value) {
            this.changingMoneyAmount = value;
        }

        int changingMoneyAmount;
    }

    @Value
    public static class MoneyChangingStatus {
        public MoneyChangingStatus(int value) {
            this.changingMoneyStatus = value;
        }

        int changingMoneyStatus;
    }

    @Value
    public static class Uuid {
        public Uuid(UUID value) {
            this.uuid = value;
        }

        UUID uuid;
    }
}
