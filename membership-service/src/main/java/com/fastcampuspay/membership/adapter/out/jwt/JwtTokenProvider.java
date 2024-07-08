package com.fastcampuspay.membership.adapter.out.jwt;

import com.fastcampuspay.membership.application.port.out.AuthMembershipPort;
import com.fastcampuspay.membership.domain.Membership;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

// 실제 jwt token 을 생성하고 검증하는 로직을 구현
@Component
public class JwtTokenProvider implements AuthMembershipPort {

    private String jwtSecret; // 내부 비밀키. 즉 jwt token 생성 및 검증을 위한 키

    private long jwtExpirationInMs; // jwt token 만료 시간

    private long refreshTokenExpirationInMs; // refresh token 만료 시간

    public JwtTokenProvider() {
        // aes 512 bit 알고리즘 지원을 위한 비밀키 입니다.
        // 512 bit = 64byte
        // env 등을 통해서 외부 환경변수로부터 데이터를 받아 올 수도 있다.
        this.jwtSecret = "1234567890123456789012345678901234567890123456789012345678901234";
        this.jwtExpirationInMs = 1000L * 20;
        this.refreshTokenExpirationInMs = 1000L * 60;
    }

    @Override
    public String generateJwtToken(Membership.MembershipId membershipId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(membershipId.getMembershipId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    @Override
    public String generateRefreshToken(Membership.MembershipId membershipId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpirationInMs);

        return Jwts.builder()
                .setSubject(membershipId.getMembershipId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    @Override
    public boolean validateJwtToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken);
            return true;
        } catch (MalformedJwtException e) {
//            log.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
//            log.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
//            log.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
//            log.error("JWT claims string is empty -> Message: {}", e);
        } catch (SignatureException e) {
//            log.error("JWT signature does not match locally computed signature -> Message: {}", e);
        }
        return false;
    }

    @Override
    public Membership.MembershipId parseMembershipIdFromToken(String jwtToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwtToken)
                .getBody();

        return new Membership.MembershipId(claims.getSubject());
    }

    @Override
    public Membership getMembershipByJwtToken(String jwtToken) {
        return null;
    }
}
