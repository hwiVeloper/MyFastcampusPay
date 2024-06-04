package com.fastcampus.payment.application.service;

import com.fastcampus.payment.application.port.in.RequestPaymentCommand;
import com.fastcampus.payment.application.port.in.RequestPaymentUseCase;
import com.fastcampus.payment.application.port.out.CreatePaymentPort;
import com.fastcampus.payment.application.port.out.GetMembershipPort;
import com.fastcampus.payment.application.port.out.GetRegisteredBankAccountPort;
import com.fastcampus.payment.domain.Payment;
import com.fastcampuspay.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class PaymentService implements RequestPaymentUseCase {

    private final CreatePaymentPort createPaymentPort;
    private final GetMembershipPort getMembershipPort;
    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;

    @Override
    public Payment requestPayment(RequestPaymentCommand command) {
        //
        // 멤버십, 뱅킹, 머니, 충전, ...
        //

        return createPaymentPort.createPayment(
                command.getRequestMembershipId(),
                command.getRequestPrice(),
                command.getFranchiseId(),
                command.getFranchiseFeeRate()
        );
    }
}
