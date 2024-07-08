package com.fastcampuspay.money.aggregation.adapter.out.service;

import com.fastcampuspay.common.CommonHttpClient;
import com.fastcampuspay.common.ExternalSystemAdapter;
import com.fastcampuspay.money.aggregation.application.port.out.GetMoneySumPort;
import com.fastcampuspay.money.aggregation.application.port.out.MemberMoney;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        String url = String.join("/", moneyServiceEndpoint, "money/member-money");
        ObjectMapper mapper = new ObjectMapper();

        try {
            FindMemberMoneyRequest request = new FindMemberMoneyRequest(membershipIds);
            String jsonResponse = moneyServiceHttpClient.sendPostRequest(url, mapper.writeValueAsString(request)).body();

            // N명 고객의, 각 고객의 MemberMoney를 가져와요.
            List<MemberMoney> memberMoneyList = mapper.readValue(jsonResponse, new TypeReference<>() {});
            return memberMoneyList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
