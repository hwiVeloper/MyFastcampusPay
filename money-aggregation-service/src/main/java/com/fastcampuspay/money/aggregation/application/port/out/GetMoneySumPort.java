package com.fastcampuspay.money.aggregation.application.port.out;

import java.util.List;

public interface GetMoneySumPort {
    // membership ids로 member money 정보를 List로 가져온다.
    List<MemberMoney> getMoneySumByMembershipIds(List<String> membershipIds);
}
