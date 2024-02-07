package com.fastcampuspay.membership.application.service;

import com.fastcampuspay.common.UseCase;
import com.fastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.fastcampuspay.membership.adapter.out.persistence.MembershipMapper;
import com.fastcampuspay.membership.application.port.in.FindMembershipCommand;
import com.fastcampuspay.membership.application.port.in.FindMembershipUseCase;
import com.fastcampuspay.membership.application.port.out.FindMembershipPort;
import com.fastcampuspay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class FindMembershipService implements FindMembershipUseCase {

    private final FindMembershipPort port;

    private final MembershipMapper mapper;

    @Override
    public Membership findMembership(FindMembershipCommand command) {
        MembershipJpaEntity entity = port.findMembership(new Membership.MembershipId(command.getMembershipId()));
        return mapper.mapToDomainEntity(entity);
    }
}
