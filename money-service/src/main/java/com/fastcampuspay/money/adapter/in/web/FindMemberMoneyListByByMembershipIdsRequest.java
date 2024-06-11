package com.fastcampuspay.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMemberMoneyListByByMembershipIdsRequest {
    private List<String> membershipIds;
}
