package com.fastcampuspay.money.application.service;

import com.fastcampuspay.common.CountDownLatchManager;
import com.fastcampuspay.common.RechargingMoneyTask;
import com.fastcampuspay.common.SubTask;
import com.fastcampuspay.common.UseCase;
import com.fastcampuspay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.fastcampuspay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.fastcampuspay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.fastcampuspay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.fastcampuspay.money.application.port.out.GetMembershipPort;
import com.fastcampuspay.money.application.port.out.IncreaseMoneyPort;
import com.fastcampuspay.money.application.port.out.SendRechargingMoneyTaskPort;
import com.fastcampuspay.money.domain.MemberMoney;
import com.fastcampuspay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@UseCase
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {

    private final GetMembershipPort membershipPort;
    private final IncreaseMoneyPort port;
    private final MoneyChangingRequestMapper mapper;

    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;

    private final CountDownLatchManager countDownLatchManager;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {
        // 머니의 충전(증액)
        // 1. 고객 정보가 정상인지 확인 (멤버)
        membershipPort.getMembership(command.getTargetMembershipId());

        // 2. 고객의 연동된 계좌가 유효한지 정상인지 확인, 잔액이 충분한 지도 확인 (뱅킹)

        // 3. 법인 계좌 상태도 정상인지 확인 (뱅킹)

        // 4. 증액을 위한 '기록'. 요청 상태로 MoneyChangingRequest를 생성한다.(MoneyChangingRequest)

        // 5. 펌뱅킹을 수행하고 (고객 연동 계좌 -> 패캠페이 법인 계좌) (뱅킹)

        // 6-1. 결과가 정상적이라면. 성공으로 MoneyChangingRequest 상태값을 변동 후에 리턴
        // 성공 시에 멤버의 MemberMoney 값 증액이 필요하다.
        MemberMoneyJpaEntity entity = port.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId()),
                command.getAmount()
        );

        if (entity != null) {
            return mapper.mapToDomainEntity(
                    port.createMoneyChangingRequest(
                            new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                            new MoneyChangingRequest.MoneyChangingType(0),
                            new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                            new MoneyChangingRequest.MoneyChangingStatus(1),
                            new MoneyChangingRequest.Uuid(UUID.randomUUID())
                    )
            );
        }

        // 6.2. 결과가 실패라면, 실패라고 MoneyChangingRequest 상태값ㅇ르 변동 후에 리턴
        return null;
    }

    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {

        // Subtask
        // 각 서비스에 특정 membershipId로 validation을 위한 task

        // 1. Subtask, Task
        SubTask validMembershipTask = SubTask.builder()
                .subTaskName("validateMemberTask: " + "멤버십 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();

        SubTask validBankingAccountTask = SubTask.builder()
                .subTaskName("validBankingAccountTask: " + "계좌 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("banking")
                .status("ready")
                .build();

        // Banking Sub task
        // Banking Account validation
        // Amount money firmbanking -> 무조건 ok 받았다고 가정.

        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(validMembershipTask);
        subTaskList.add(validBankingAccountTask);

        RechargingMoneyTask task = RechargingMoneyTask.builder()
                .taskID(UUID.randomUUID().toString())
                .taskName("rechargingMoneyTask")
                .subTaskList(subTaskList)
                .moneyAmount(command.getAmount())
                .membershipID(command.getTargetMembershipId())
                .toBankName("fastcampus")
                .build();

        // 2. Kafka Cluster Produce
        sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(task);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 3. Wait
        try {
            countDownLatchManager.getCountDownLatch(task.getTaskID()).await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 3-1. task-consumer
        // 등록된 subtask, status 모두 ok -> task 결과를 Produce

        // 4. Task result consume
        // conutDownLatchManager에다가 응답 결과를 받아야 한다.
        String result = countDownLatchManager.getDataForKey(task.getTaskID());
        if ("success".equals(result)) {
            MemberMoneyJpaEntity entity = port.increaseMoney(
                    new MemberMoney.MembershipId(command.getTargetMembershipId()),
                    command.getAmount()
            );

            if (entity != null) {
                return mapper.mapToDomainEntity(
                        port.createMoneyChangingRequest(
                                new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                                new MoneyChangingRequest.MoneyChangingType(0),
                                new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                                new MoneyChangingRequest.MoneyChangingStatus(1),
                                new MoneyChangingRequest.Uuid(UUID.randomUUID())
                        )
                );
            }
        } else {
            // fail
            return null;
        }

        // 5. Consume ok, logic...
        return null;
    }
}
