package com.fastcampuspay.banking.adapter.in.web;

import com.fastcampuspay.banking.application.port.in.RegisterBankAccountCommand;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingCommand;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingUseCase;
import com.fastcampuspay.banking.domain.RegisteredBankAccount;
import com.fastcampuspay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/banking")
public class RequestFirmbankingController {

    private final RequestFirmbankingUseCase useCase;

    @PostMapping(path = "/firmbanking/request")
    RegisteredBankAccount request(@RequestBody RequestFirmbankingRequest request) {
        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
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