package com.fastcampuspay.banking.application.port.out;

import com.fastcampuspay.banking.adapter.out.service.MembershipStatus;

public interface GetMembershipPort {

    public MembershipStatus getMembership(String membershipId);
}
