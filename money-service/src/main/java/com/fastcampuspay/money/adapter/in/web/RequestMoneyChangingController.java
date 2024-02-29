package com.fastcampuspay.money.adapter.in.web;

import com.fastcampuspay.common.WebAdapter;
import com.fastcampuspay.money.application.port.in.RegisterBankAccountCommand;
import com.fastcampuspay.money.application.port.in.RegisterBankAccountUseCase;
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

    private final RegisterBankAccountUseCase useCase;

    @PostMapping(path = "/account/register")
    RegisteredBankAccount increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {
        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .membershipId(request.getMembershipId())
                .bankName(request.getBankName())
                .bankAccountNumber(request.getBankAccountNumber())
                .linkedStatusIsValid(request.isLinkedStatusIsValid())
                .build();

        RegisteredBankAccount registeredBankAccount = useCase.registerBankAccount(command);
        if (registeredBankAccount == null) {
            // TODO: Error Handling
            throw new RuntimeException("등록 실패");
        }

        return registeredBankAccount;
    }
}

// TODO: 숙제 find하는거...