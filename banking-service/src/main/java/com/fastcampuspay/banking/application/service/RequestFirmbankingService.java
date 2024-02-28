package com.fastcampuspay.banking.application.service;

import com.fastcampuspay.banking.adapter.out.external.bank.BankAccount;
import com.fastcampuspay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.fastcampuspay.banking.adapter.out.persistence.FirmbankingRequestMapper;
import com.fastcampuspay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.fastcampuspay.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.fastcampuspay.banking.application.port.in.RegisterBankAccountCommand;
import com.fastcampuspay.banking.application.port.in.RegisterBankAccountUseCase;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingCommand;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingUseCase;
import com.fastcampuspay.banking.application.port.out.RegisterBankAccountPort;
import com.fastcampuspay.banking.application.port.out.RequestBankAccountInfoPort;
import com.fastcampuspay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.fastcampuspay.banking.application.port.out.RequestFirmbankingPort;
import com.fastcampuspay.banking.domain.FirmbankingRequest;
import com.fastcampuspay.banking.domain.RegisteredBankAccount;
import com.fastcampuspay.common.UseCase;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@UseCase
public class RequestFirmbankingService implements RequestFirmbankingUseCase {

    private final FirmbankingRequestMapper mapper;

    private final RequestFirmbankingPort port;

    private final RequestExternalFirmbankingPort requestExcernalFirmbankingPort;

    @Override
    public FirmbankingRequest requestFirmbanking(RequestFirmbankingCommand command) {
        // a -> b 계좌
        // 1. 요청에 대한 정보를 먼저 write . "요청" 상태로

        // 2. 외부 은행에 펌뱅킹 요청

        // 3. 결과에 따라서 1번에서 작성했던 FirmbankingRequest 정보를 update

        // 4. 결과를 리턴


        return null;
    }
}
