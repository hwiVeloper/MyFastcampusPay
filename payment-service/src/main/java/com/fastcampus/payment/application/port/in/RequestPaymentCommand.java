package com.fastcampus.payment.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPaymentCommand {
    private String requestMembershipId;
    private String requestPrice;
    private String franchiseId;
    private String franchiseFeeRate;
//    private int paymentStatus;
//    private Date approvedAt;
}
