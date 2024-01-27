package com.fastcampuspay.membership.adapter.in.web;

import common.WebAdapter;
import lombok.RequiredArgsConstructor;
import com.fastcampuspay.membership.application.port.in.RegisterMembershipCommand;
import com.fastcampuspay.membership.application.port.in.RegisterMembershipUseCase;
import com.fastcampuspay.membership.domain.Membership;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/membership")
public class RegisterMembershipController {

    private final RegisterMembershipUseCase useCase;

    @PostMapping(path = "/register")
    Membership register(@RequestBody RegisterMembershipRequest request) {
        // request
        // request -> Command
        // UseCase ~~ request x , command

        RegisterMembershipCommand command = RegisterMembershipCommand.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .isValid(true)
                .isCorp(request.isCorp())
                .build();

        return useCase.registerMembership(command);
    }
}
