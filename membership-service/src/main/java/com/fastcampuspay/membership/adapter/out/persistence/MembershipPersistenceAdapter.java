package com.fastcampuspay.membership.adapter.out.persistence;

import com.fastcampuspay.common.PersistenceAdapter;
import com.fastcampuspay.membership.application.port.out.FindMembershipPort;
import com.fastcampuspay.membership.application.port.out.ModifyMembershipPort;
import com.fastcampuspay.membership.application.port.out.RegisterMembershipPort;
import com.fastcampuspay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

    private final SpringDataMembershipRepository repository;

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName
            , Membership.MembershipEmail membershipEmail
            , Membership.MembershipAddress membershipAddress
            , Membership.MembershipIsValid membershipIsValid
            , Membership.MembershipIsCorp membershipIsCorp) {
        return repository.save(
            new MembershipJpaEntity(
                membershipName.getMembershipName()
                , membershipEmail.getMembershipEmail()
                , membershipAddress.getMembershipAddress()
                , membershipIsValid.isMembershipIsValid()
                , membershipIsCorp.isMembershipIsCorp()
            )
        );
    }

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {
        return repository.getById(Long.parseLong(membershipId.getMembershipId()));
    }


    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipId membershipId, Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {
        MembershipJpaEntity entity = repository.getById(Long.parseLong(membershipId.getMembershipId()));

        entity.setName(membershipName.getMembershipName());
        entity.setAddress(membershipAddress.getMembershipAddress());
        entity.setEmail(membershipEmail.getMembershipEmail());
        entity.setCorp(membershipIsCorp.isMembershipIsCorp());
        entity.setValid(membershipIsValid.isMembershipIsValid());

        return repository.save(entity);
    }
}
