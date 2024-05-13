package com.fastcampuspay.money.application.port.in;

import com.fastcampuspay.money.domain.MemberMoney;

public interface CreateMemberMoneyPort {
    void crateMemberMoney(
            MemberMoney.MembershipId memberId,
            MemberMoney.MoneyAggregateIdentifier aggregateIdentifier
    );
}
