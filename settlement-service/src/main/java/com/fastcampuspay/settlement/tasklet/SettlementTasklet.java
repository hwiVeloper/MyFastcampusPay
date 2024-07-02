package com.fastcampuspay.settlement.tasklet;

import com.fastcampuspay.settlement.adapter.out.service.Payment;
import com.fastcampuspay.settlement.port.out.GetRegisteredBankAccountPort;
import com.fastcampuspay.settlement.port.out.PaymentPort;
import com.fastcampuspay.settlement.port.out.RegisteredBankAccountAggregateIdentifier;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SettlementTasklet implements  Tasklet{

    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;

    private final PaymentPort paymentPort;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext){


        System.out.println("Tasklet Execute!: "
                + chunkContext.getStepContext()
                .getStepExecution()
                .getJobParameters()
                .getLong("time"));

        // Business Logic
//        RegisteredBankAccountAggregateIdentifier entity = getRegisteredBankAccountPort.getRegisteredBankAccount("1");
//        System.out.println(entity);
//        getRegisteredBankAccountPort.requestFirmbanking("국민은행", "123-456-789", 10000);

//        paymentPort.finishSettlement(29L);

        // Payment
        // 1. 정상 결제 건 조회
        // 2. 특정 결제 건에 대해서, 상태값을 정산 완료로 변경.
        // Banking
        // 1. 등록된 계좌 정보 가져오기
        // 2.정산 금액만큼 계좌에 입금 요청 (펌뱅킹)

        // 1. payment service 에서 결제 완료된 결제 내역을 조회
        List<Payment> normalStatusPaymentList = paymentPort.getNormalStatusPayments();

        // 2. 각 결제 내역의 franchiseId에 해당하는 멤버십 정보에 대한 뱅킹 정보(계좌번호)를 가져와서
        Map<String, FirmbankingRequestInfo> franchiseIdToBankAccountMap = new HashMap<>();
        for (Payment payment : normalStatusPaymentList) {
            RegisteredBankAccountAggregateIdentifier entity = getRegisteredBankAccountPort.getRegisteredBankAccount(payment.getFranchiseId());
            franchiseIdToBankAccountMap.put(payment.getFranchiseId(), new FirmbankingRequestInfo(entity.getBankName(), entity.getBankAccountNumber()));

        }

        // 3. 각 franchiseId 별로 정산 금액을 계산해주고
        for (Payment payment : normalStatusPaymentList) {
            FirmbankingRequestInfo firmbankingRequestInfo = franchiseIdToBankAccountMap.get(payment.getFranchiseId());
            double fee = Double.parseDouble(payment.getFranchiseFeeRate());
            int caculatedPrice = (int) ((100 - fee) * payment.getRequestPrice() * 100);
            firmbankingRequestInfo.setMoneyAmount(firmbankingRequestInfo.getMoneyAmount() + caculatedPrice);
        }

        // 4. 계산된 금액을 펌뱅킹 요청해주고
        for (FirmbankingRequestInfo firmbankingRequestInfo : franchiseIdToBankAccountMap.values()) {
            getRegisteredBankAccountPort.requestFirmbanking(
                    firmbankingRequestInfo.getBankName()
                    , firmbankingRequestInfo.getBankAccountNumber()
                    , firmbankingRequestInfo.getMoneyAmount());
        }

        // 5. 정산 완료된 결제 내역은 정산 완료 상태로 변경해준다.
        for (Payment payment : normalStatusPaymentList) {
            paymentPort.finishSettlement(payment.getPaymentId());
        }

        return RepeatStatus.FINISHED;
    }
}
