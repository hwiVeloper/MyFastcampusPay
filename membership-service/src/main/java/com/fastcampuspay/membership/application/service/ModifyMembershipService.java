package com.fastcampuspay.membership.application.service;

import com.fastcampuspay.common.UseCase;
import com.fastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.fastcampuspay.membership.adapter.out.persistence.MembershipMapper;
import com.fastcampuspay.membership.application.port.in.ModifyMembershipCommand;
import com.fastcampuspay.membership.application.port.in.ModifyMembershipUseCase;
import com.fastcampuspay.membership.application.port.out.ModifyMembershipPort;
import com.fastcampuspay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@UseCase
public class ModifyMembershipService implements ModifyMembershipUseCase {

    private final ModifyMembershipPort port;

    private final MembershipMapper mapper;

    @Override
    public Membership modifyMembership(ModifyMembershipCommand command) {
        MembershipJpaEntity jpaEntity = port.modifyMembership(
                new Membership.MembershipId(command.getMembershipId())
                , new Membership.MembershipName(command.getName())
                , new Membership.MembershipEmail(command.getEmail())
                , new Membership.MembershipAddress(command.getAddress())
                , new Membership.MembershipIsValid(command.isValid())
                , new Membership.MembershipIsCorp(command.isCorp())
        );

        // entity -> mapper
        return mapper.mapToDomainEntity(jpaEntity);
    }
}
