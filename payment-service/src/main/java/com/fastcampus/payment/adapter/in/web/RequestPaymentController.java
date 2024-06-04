package com.fastcampus.payment.adapter.in.web;

import com.fastcampus.payment.application.port.in.RequestPaymentCommand;
import com.fastcampus.payment.application.port.in.RequestPaymentUseCase;
import com.fastcampus.payment.domain.Payment;
import com.fastcampuspay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestPaymentController {

    private final RequestPaymentUseCase requestPaymentUseCase;

    @PostMapping(path = "/payment/request")
    void requestPayment(PaymentRequest request) {
        Payment payment = requestPaymentUseCase.requestPayment(
                new RequestPaymentCommand(
                        request.getRequestMembershipId(),
                        request.getRequestPrice(),
                        request.getFranchiseId(),
                        request.getFranchiseFeeRate()
                )
        );
    }
}
