package com.fastcampuspay.membership.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class JwtToken {
    @Getter private final String membershipId;
    @Getter private final String jwtToken;
    @Getter private final String refreshToken;

    public static JwtToken generateJwtToken(
            MembershipId membershipId,
            MembershipJwtToken jwtToken,
            MembershipRefreshToken refreshToken
    ) {
        return new JwtToken(
                membershipId.getMembershipId(),
                jwtToken.getJwtToken(),
                refreshToken.getRefreshToken()
        );
    }

    @Value
    public static class MembershipId {
        public MembershipId(String value) {
            this.membershipId = value;
        }
        String membershipId;
    }

    @Value
    public static class MembershipJwtToken {
        public MembershipJwtToken(String value) {
            this.jwtToken = value;
        }
        String jwtToken;
    }

    @Value
    public static class MembershipRefreshToken {
        public MembershipRefreshToken(String value) {
            this.refreshToken = value;
        }
        String refreshToken;
    }
}
