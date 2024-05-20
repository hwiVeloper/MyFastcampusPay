package com.fastcampuspay.banking.application.service;

import com.fastcampuspay.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import com.fastcampuspay.banking.adapter.axon.command.UpdateFirmbankingRequestCommand;
import com.fastcampuspay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.fastcampuspay.banking.adapter.out.external.bank.FirmbankingResult;
import com.fastcampuspay.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import com.fastcampuspay.banking.adapter.out.persistence.FirmbankingRequestMapper;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingCommand;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingUseCase;
import com.fastcampuspay.banking.application.port.in.UpdateFirmbankingCommand;
import com.fastcampuspay.banking.application.port.in.UpdateFirmbankingUseCase;
import com.fastcampuspay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.fastcampuspay.banking.application.port.out.RequestFirmbankingPort;
import com.fastcampuspay.banking.domain.FirmbankingRequest;
import com.fastcampuspay.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.transaction.Transactional;

import java.util.UUID;

import static org.apache.kafka.common.protocol.types.Type.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase, UpdateFirmbankingUseCase {
    private final FirmbankingRequestMapper mapper;
    private final RequestFirmbankingPort requestFirmbankingPort;

    private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;

    private final CommandGateway commandGateway;

    @Override
    public FirmbankingRequest requestFirmbanking(RequestFirmbankingCommand command) {

        // Business Logic
        // a -> b 계좌

        // 1. 요청에 대해 정보를 먼저 write . "요청" 상태로
        FirmbankingRequestJpaEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.FromBankName(command.getFromBankName()),
                new FirmbankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmbankingRequest.ToBankName(command.getToBankName()),
                new FirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(0),
                new FirmbankingRequest.AggregateIdentifier("")
        );

        // 2. 외부 은행에 펌뱅킹 요청
        FirmbankingResult result = requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber()
        ));

        // Transactional UUID
        java.util.UUID randomUUID = java.util.UUID.randomUUID();
        requestedEntity.setUuid(randomUUID.toString());

        // 3. 결과에 따라서 1번에서 작성했던 FirmbankingRequest 정보를 Update
        if (result.getResultCode() == 0){
            // 성공
            requestedEntity.setFirmbankingStatus(1);
        } else {
            // 실패
            requestedEntity.setFirmbankingStatus(2);
        }

        // 4. 결과를 리턴하기 전에 바뀐 상태 값을 기준으로 다시 save
        return mapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity), randomUUID);
    }

    @java.lang.Override
    public FirmbankingRequest requestFirmbankingByEvent(RequestFirmbankingCommand command) {
        CreateFirmbankingRequestCommand createFirmbankingRequestCommand = CreateFirmbankingRequestCommand.builder()
                .toBankName(command.getToBankName())
                .toBankAccountNumber(command.getToBankAccountNumber())
                .fromBankName(command.getFromBankName())
                .fromBankAccountNumber(command.getFromBankAccountNumber())
                .moneyAmount(command.getMoneyAmount())
                .build();

        commandGateway.send(createFirmbankingRequestCommand).whenComplete(
                (result, throwable) -> {
                    if (throwable != null) {
                        // fail
                        throwable.printStackTrace();
                        throw new RuntimeException(throwable);
                    } else {
                        // success
                        System.out.println("result aggregate id = " + result.toString());
                        // request firmbanking db save
                        FirmbankingRequestJpaEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
                                new FirmbankingRequest.FromBankName(command.getFromBankName()),
                                new FirmbankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                                new FirmbankingRequest.ToBankName(command.getToBankName()),
                                new FirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                                new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                                new FirmbankingRequest.FirmbankingStatus(0),
                                new FirmbankingRequest.AggregateIdentifier("")
                        );

                        // 2. 은행에 펌뱅킹 요청..
                        FirmbankingResult firmbankingResult = requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                                command.getFromBankName(),
                                command.getFromBankAccountNumber(),
                                command.getToBankName(),
                                command.getToBankAccountNumber()
                        ));

                        java.util.UUID randomUUID = java.util.UUID.randomUUID();
                        requestedEntity.setUuid(randomUUID.toString());

                        // 3. 결과에 따라서 1번에서 작성했던 FirmbankingRequest 정보를 Update
                        if (firmbankingResult.getResultCode() == 0){
                            // 성공
                            requestedEntity.setFirmbankingStatus(1);
                        } else {
                            // 실패
                            requestedEntity.setFirmbankingStatus(2);
                        }

                        mapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity), randomUUID);
                    }
                }
        );
        return null;
    }

    @Override
    public void updateFirmbankingByEvent(UpdateFirmbankingCommand command) {
        UpdateFirmbankingRequestCommand updateFirmbankingRequestCommand = new UpdateFirmbankingRequestCommand(
                command.getFirmbankingAggregateIdentifier(),
                command.getFirmbankingStatus()
        );

        commandGateway.send(updateFirmbankingRequestCommand)
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                    } else {
                        System.out.println("updateFirmbankingRequestCommand completed, Aggregate ID : " + result);
                        FirmbankingRequestJpaEntity entity = requestFirmbankingPort.getFirmbankingRequest(
                                new FirmbankingRequest.AggregateIdentifier(command.getFirmbankingAggregateIdentifier())
                        );

                        // status 의 변경으로 인한 외부 은행과의 커뮤니케이션
                        // if rollback -> 0, status 변경도 해주겠지만
                        // + 기존 펌뱅킹 정보에서 from <-> to 가 변경된 펌뱅킹을 요청하는 새로운 요청
                        entity.setFirmbankingStatus(command.getFirmbankingStatus());
                        requestFirmbankingPort.modifyFirmbankingRequest(entity);
                    }
                });
    }
}
