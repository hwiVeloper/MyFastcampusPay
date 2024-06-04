package com.fastcampus.payment.application.port.out;

import com.fastcampus.payment.domain.Payment;

public interface CreatePaymentPort {
    Payment createPayment(String requestMembershipId, String requestPrice, String franchiseId, String franchiseFeeRate);
}
