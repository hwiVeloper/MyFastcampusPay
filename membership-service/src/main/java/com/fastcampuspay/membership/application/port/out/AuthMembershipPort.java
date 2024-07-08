package com.fastcampuspay.membership.application.port.out;

import com.fastcampuspay.membership.domain.Membership;

public interface AuthMembershipPort {
    // membershipId 기준으로 jwt token 생성
    String generateJwtToken(Membership.MembershipId membershipId);

    // membershipId 기준으로 refresh token 생성
    String generateRefreshToken(Membership.MembershipId membershipId);

    // jwt token 을 검증
    boolean validateJwtToken(String jwtToken);

    // jwtToken 으로 어떤 membershipId 인지 parsing 및 확인
    Membership.MembershipId parseMembershipIdFromToken(String jwtToken);

    Membership getMembershipByJwtToken(String jwtToken);
}
