package com.fastcampuspay.membership.application.service;

import com.fastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.fastcampuspay.membership.adapter.out.persistence.MembershipMapper;
import com.fastcampuspay.membership.application.port.in.RegisterMembershipCommand;
import com.fastcampuspay.membership.application.port.in.RegisterMembershipUseCase;
import com.fastcampuspay.membership.application.port.out.RegisterMembershipPort;
import com.fastcampuspay.membership.domain.Membership;
import common.UseCase;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@UseCase
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final RegisterMembershipPort port;

    private final MembershipMapper mapper;

    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {

        // command -> DB와 통신
        // 통신한 결과값을 리턴

        // biz logic -> DB
        // external system, port와 adapter를 통해 외부 통신
        // port, adapter

        MembershipJpaEntity jpaEntity = port.createMembership(
                new Membership.MembershipName(command.getName())
                , new Membership.MembershipEmail(command.getEmail())
                , new Membership.MembershipAddress(command.getAddress())
                , new Membership.MembershipIsValid(command.isValid())
                , new Membership.MembershipIsCorp(command.isCorp())
        );

        // entity -> mapper
        return mapper.mapToDomainEntity(jpaEntity);
    }
}
