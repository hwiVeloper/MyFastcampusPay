package com.fastcampuspay.money.aggregation.application.service;

import com.fastcampuspay.common.UseCase;
import com.fastcampuspay.money.aggregation.application.port.in.GetMoneySumByAddressCommand;
import com.fastcampuspay.money.aggregation.application.port.in.GetMoneySumByAddressUseCase;
import com.fastcampuspay.money.aggregation.application.port.out.GetMembershipPort;
import com.fastcampuspay.money.aggregation.application.port.out.GetMoneySumPort;
import com.fastcampuspay.money.aggregation.application.port.out.MemberMoney;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional
public class GetMoneySumByAggregationService implements GetMoneySumByAddressUseCase {

    private final GetMoneySumPort getMoneySumPort;
    private final GetMembershipPort getMembershipPort;

    @Override
    public int getMoneySumByAddress(GetMoneySumByAddressCommand command) {
        // Aggregation을 위한 비즈니스 로직

        // 강남구, 서초구, 관악구
        String targetAddress = command.getAddress();

        List<String> membershipIds = getMembershipPort.getMembershipByAddress(targetAddress);

        int chunkCount = 0;
        if (membershipIds.size() > 100) {
            // 100개씩 나눠서, 요청한다.
            chunkCount = (membershipIds.size() / 100) + 1; //
        }

        // 100개씩 짤라서 요청.


        // 100개씩 요청해서, 값을 계산하기로 설계.
        List<MemberMoney> memberMoneyList = getMoneySumPort.getMoneySumByMembershipIds(membershipIds);

        int sum = 0;
        for (MemberMoney memberMoney : memberMoneyList) {
            sum += memberMoney.getBalance();
        }

        return sum;
    }
}
