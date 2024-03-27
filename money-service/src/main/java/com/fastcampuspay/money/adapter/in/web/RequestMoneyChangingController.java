package com.fastcampuspay.money.adapter.in.web;

import com.fastcampuspay.common.WebAdapter;
import com.fastcampuspay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.fastcampuspay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.fastcampuspay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/money")
public class RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase iUseCase;

    // private final DecreaseMoneyRequestUseCase dUseCase;

    @PostMapping(path = "/increase")
    MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = iUseCase.increaseMoneyRequest(command);
        // MoneyChangingRequest -> MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,
                1,
                moneyChangingRequest.getChangingMoneyAmount()
        );

        return resultDetail;
    }

    @PostMapping(path = "/increase-async")
    MoneyChangingResultDetail increaseMoneyChangingRequestAsync(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = iUseCase.increaseMoneyRequestAsync(command);
        // MoneyChangingRequest -> MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,
                1,
                moneyChangingRequest.getChangingMoneyAmount()
        );

        return resultDetail;
    }

    @PostMapping(path = "/decrease")
    MoneyChangingResultDetail decreaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {
//        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
//                .membershipId(request.getMembershipId())
//                .bankName(request.getBankName())
//                .bankAccountNumber(request.getBankAccountNumber())
//                .linkedStatusIsValid(request.isLinkedStatusIsValid())
//                .build();
//
//        RegisteredBankAccount registeredBankAccount = iUseCase.registerBankAccount(command);
//        if (registeredBankAccount == null) {
//            // TODO: Error Handling
//            throw new RuntimeException("등록 실패");
//        }
//
//        return registeredBankAccount;
        return null;
    }
}