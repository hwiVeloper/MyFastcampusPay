package com.fastcampuspay.money.adapter.out.persistence;

import com.fastcampuspay.money.adapter.in.web.MoneyChangingResultDetail;
import com.fastcampuspay.money.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

@Component
public class MoneyChangingRequestMapper {
    public MoneyChangingRequest mapToDomainEntity(MoneyChangingRequestJpaEntity jpaEntity) {
        return MoneyChangingRequest.generateMoneyChangingRequest(
                new MoneyChangingRequest.MoneyChangingRequestId(jpaEntity.getMoneyChangingRequestId()+""),
                new MoneyChangingRequest.TargetMembershipId(jpaEntity.getTargetMembershipId()),
                new MoneyChangingRequest.MoneyChangingType(jpaEntity.getMoneyChangingType()),
                new MoneyChangingRequest.ChangingMoneyAmount(jpaEntity.getMoneyAmount()),
                new MoneyChangingRequest.MoneyChangingStatus(jpaEntity.getChangingMoneyStatus()),
                new MoneyChangingRequest.Uuid(jpaEntity.getUuid())
        );
    }
}
