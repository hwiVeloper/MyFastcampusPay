package com.fastcampuspay.membership.adapter.in.web;

import com.fastcampuspay.common.WebAdapter;
import com.fastcampuspay.membership.application.port.in.AuthMembershipUseCase;
import com.fastcampuspay.membership.application.port.in.LoginMembershipCommand;
import com.fastcampuspay.membership.application.port.in.RefreshTokenCommand;
import com.fastcampuspay.membership.application.port.in.ValidateTokenCommand;
import com.fastcampuspay.membership.domain.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class AuthMembershipController {

    private final AuthMembershipUseCase authMembershipUseCase;

    @PostMapping(path = "/membership/login")
    JwtToken loginMembership(@RequestBody LoginMembershipRequest loginMembershipRequest) {
        LoginMembershipCommand command = LoginMembershipCommand.builder()
                .membershipId(loginMembershipRequest.getMembershipId())
                .build();

        return authMembershipUseCase.loginMembership(command);
    }

    @PostMapping(path = "/membership/refresh-token")
    JwtToken refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshTokenCommand command = RefreshTokenCommand.builder()
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .build();
       return authMembershipUseCase.refreshJwtTokenByRefreshToken(command);
    }

    @PostMapping(path = "/membership/token-validate")
    boolean tokenValidate(@RequestBody ValidateTokenRequest validateTokenRequest) {
        ValidateTokenCommand command = ValidateTokenCommand.builder()
                .jwtToken(validateTokenRequest.getJwtToken())
                .build();

        return authMembershipUseCase.validateJwtToken(command);
    }
}
