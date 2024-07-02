package com.fastcampuspay.payment.application.port.in;

import com.fastcampuspay.payment.domain.Payment;

import java.util.List;

public interface RequestPaymentUseCase {
    Payment requestPayment(RequestPaymentCommand command);

    // 원래대로라면 커맨드 있어야 하고 커맨드에 시작/종료 일자 정도 있어야 함.
    List<Payment> getNormalStatusPayments();

    void finishPayment(FinishSettlementCommand command);
}
