package com.fastcampuspay.money.adapter.in.axon.event;

import com.fastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class MemberMoneyCreatedEvent extends SelfValidating<MemberMoneyCreatedEvent> {
    @NotNull
    private final String membershipId;

    public MemberMoneyCreatedEvent(@NotNull String targetMembershipId) {
        this.membershipId = targetMembershipId;
        this.validateSelf();
    }
}
