package com.fastcampuspay.money.aggregation.adapter.out.service;

import com.fastcampuspay.common.CommonHttpClient;
import com.fastcampuspay.money.aggregation.application.port.out.GetMembershipPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MembershipServiceAdapter implements GetMembershipPort {

    private final CommonHttpClient commonHttpClient;

    private final String membershipServiceUrl;

    public MembershipServiceAdapter(CommonHttpClient commonHttpClient,
                                     @Value("${service.membership.url}") String membershipServiceUrl) {
        this.commonHttpClient = commonHttpClient;
        this.membershipServiceUrl = membershipServiceUrl;
    }

    @Override
    public List<String> getMembershipByAddress(String address) {
        String url = String.join("/", membershipServiceUrl, "membership");

        try {
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();

            ObjectMapper objectMapper = new ObjectMapper();
             Membership membership = objectMapper.readValue(jsonResponse, Membership.class);

            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
