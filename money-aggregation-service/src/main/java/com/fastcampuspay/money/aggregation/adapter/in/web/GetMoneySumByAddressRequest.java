package com.fastcampuspay.money.aggregation.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMoneySumByAddressRequest {
    private String address;
}
