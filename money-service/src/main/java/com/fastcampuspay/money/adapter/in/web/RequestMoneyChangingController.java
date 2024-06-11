package com.fastcampuspay.money.adapter.in.web;

import com.fastcampuspay.common.WebAdapter;
import com.fastcampuspay.money.application.port.in.*;
import com.fastcampuspay.money.domain.MemberMoney;
import com.fastcampuspay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {
    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;

    // private final DecreaseMoneyRequestUseCase decreaseMoneyRequestUseCase;

    private final CreateMemberMoneyUseCase createMemberMoneyUseCase;
    @PostMapping(path = "/money/increase")
    MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequest(command);

        // MoneyChangingRequest -> MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
    0,
                0,
                moneyChangingRequest.getChangingMoneyAmount());
        return resultDetail;
    }

    @PostMapping(path = "/money/increase-async")
    MoneyChangingResultDetail increaseMoneyChangingRequestAsync(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequestAsync(command);

        // MoneyChangingRequest -> MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,
                0,
                moneyChangingRequest.getChangingMoneyAmount());
        return resultDetail;
    }

    @PostMapping(path = "/money/decrease")
    MoneyChangingResultDetail decreaseMoneyChangingRequest(@RequestBody DecreaseMoneyChangingRequest request) {
//        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
//                .membershipId(request.getMembershipId())
//                .bankName(request.getBankName())
//                .bankAccountNumber(request.getBankAccountNumber())
//                .isValid(request.isValid())
//                .build();

        // registeredBankAccountUseCase.registerBankAccount(command)
        // -> MoneyChangingResultDetail
        // return decreaseMoneyRequestUseCase.decreaseMoneyChangingRequest(command);
        return null;
    }

    @PostMapping(path = "/money/create-member-money")
    void createMemberMoney (@RequestBody CreateMemberMoneyRequest request) {
        createMemberMoneyUseCase.createMemberMoney(
                CreateMemberMoneyCommand.builder().membershipId(request.getMembershipId()).build());
    }

    @PostMapping(path = "/money/increase-eda")
    void increaseMoneyChangingRequestByEvent(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        increaseMoneyRequestUseCase.increaseMoneyRequestByEvent(command);
    }

    @PostMapping(path = "/money/decrease-eda")
    void decreaseMoneyChangingRequestByEvent(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount() * -1)
                .build();

        increaseMoneyRequestUseCase.increaseMoneyRequestByEvent(command);
    }

    @PostMapping("/money/member-money")
    List<MemberMoney> getMemberMoneyListByMembershipIds(@RequestBody FindMemberMoneyListByByMembershipIdsRequest request) {
        FindMemberMoneyListByMembershipIdsCommand command = FindMemberMoneyListByMembershipIdsCommand.builder()
                .membershipIds(request.getMembershipIds())
                .build();

        return increaseMoneyRequestUseCase.findMemberMoneyListByMembershipIds(command);
    }

}
