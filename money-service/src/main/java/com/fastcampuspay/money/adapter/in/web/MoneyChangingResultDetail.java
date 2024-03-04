package com.fastcampuspay.money.adapter.in.web;

import com.fastcampuspay.money.domain.MoneyChangingRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingResultDetail {
    private String moneyChangingRequestId;
    // 증액, 감액
    private int moneyChangingType; // 0: 증액, 1: 감액
    private int moneyChangingResultStatus;
    private int amount;
}

enum MoneyChangingType {
    INCREASING,
    DECREASING
}

enum MoneyChangingResultStatus {
    SUCCEEDED,
    FAILED,
    FAILED_NOT_ENOUGH_MONEY, // 실패 - 잔액 부족
    FAILED_NOT_EXIST_MEMBERSHIP,
    FAILED_NOT_EXIST_MONEY_CHANGING_REQUEST
}