package com.fastcampuspay.money.aggregation.adapter.out.service;

import com.fastcampuspay.common.CommonHttpClient;
import com.fastcampuspay.common.ExternalSystemAdapter;
import com.fastcampuspay.money.aggregation.application.port.out.GetMoneySumPort;
import com.fastcampuspay.money.aggregation.application.port.out.MemberMoney;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class MoneyServiceAdapter implements GetMoneySumPort {

    private final CommonHttpClient moneyServiceHttpClient;

    @Value("${service.money.url}")
    private String moneyServiceEndpoint;

    public MoneyServiceAdapter(CommonHttpClient moneyServiceHttpClient, @Value("service.money.url") String moneyServiceEndpoint) {
        this.moneyServiceHttpClient = moneyServiceHttpClient;
        this.moneyServiceEndpoint = moneyServiceEndpoint;
    }

    @Override
    public List<MemberMoney> getMoneySumByMembershipIds(List<String> membershipIds) {
        return null;
    }
}
