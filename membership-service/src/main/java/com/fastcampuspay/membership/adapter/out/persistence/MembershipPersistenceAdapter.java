package com.fastcampuspay.membership.adapter.out.persistence;

import com.fastcampuspay.common.PersistenceAdapter;
import com.fastcampuspay.membership.adapter.out.vault.VaultAdapter;
import com.fastcampuspay.membership.application.port.out.FindMembershipPort;
import com.fastcampuspay.membership.application.port.out.ModifyMembershipPort;
import com.fastcampuspay.membership.application.port.out.RegisterMembershipPort;
import com.fastcampuspay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;

    private final VaultAdapter vaultAdapter;

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp, Membership.MembershipRefreshToken membershipRefreshToken) {
        String encryptedEmail = vaultAdapter.encrypt(membershipEmail.getEmailValue());
        MembershipJpaEntity savedEntity = membershipRepository.save(
                new MembershipJpaEntity(
                        membershipName.getNameValue(),
                        membershipAddress.getAddressValue(),
                        encryptedEmail,
                        membershipIsValid.isValidValue(),
                        membershipIsCorp.isCorpValue(),
                        ""
                )
        );

        MembershipJpaEntity clone = savedEntity.clone();
        clone.setEmail(vaultAdapter.decrypt(savedEntity.getEmail()));

        return clone;
    }

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {
        MembershipJpaEntity membershipJpaEntity = membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
        String encryptedEmail = membershipJpaEntity.getEmail();
        String decryptedEmail = vaultAdapter.decrypt(encryptedEmail);
        MembershipJpaEntity clone = membershipJpaEntity.clone();
        clone.setEmail(decryptedEmail);
        return clone;
    }

    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipId membershipId, Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp, Membership.MembershipRefreshToken membershipRefreshToken) {
        MembershipJpaEntity entity = membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));

        String encryptedEmail = vaultAdapter.encrypt(membershipEmail.getEmailValue());

        entity.setName(membershipName.getNameValue());
        entity.setAddress(membershipAddress.getAddressValue());
        entity.setEmail(encryptedEmail);
        entity.setCorp(membershipIsCorp.isCorpValue());
        entity.setValid(membershipIsValid.isValidValue());
        entity.setRefreshToken(membershipRefreshToken.getRefreshToken());

        MembershipJpaEntity savedEntity = membershipRepository.save(entity);
        MembershipJpaEntity clone = savedEntity.clone();
        clone.setEmail(vaultAdapter.decrypt(savedEntity.getEmail()));
        return clone;
    }

    @Override
    public List<MembershipJpaEntity> findMembershipListByAddress(Membership.MembershipAddress membershipAddress) {
        String address = membershipAddress.getAddressValue();
        List<MembershipJpaEntity> membershipJpaEntityList = membershipRepository.findByAddress(address);
        List<MembershipJpaEntity> cloneList = new ArrayList<>();

        for (MembershipJpaEntity membershipJpaEntity : membershipJpaEntityList) {
            String encryptedEmail = membershipJpaEntity.getEmail();
            String decryptedEmail = vaultAdapter.decrypt(encryptedEmail);

            MembershipJpaEntity clone = membershipJpaEntity.clone();
            clone.setEmail(decryptedEmail);
            cloneList.add(clone);
        }

        return cloneList;
    }
}
