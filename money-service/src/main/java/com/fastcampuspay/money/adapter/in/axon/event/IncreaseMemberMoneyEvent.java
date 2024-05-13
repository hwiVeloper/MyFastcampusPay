package com.fastcampuspay.money.adapter.in.axon.event;

import com.fastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class IncreaseMemberMoneyEvent extends SelfValidating<IncreaseMemberMoneyEvent> {

    @NotNull
    private String aggregateIdentifier;
    @NotNull
    private String targetMembershipId;
    private int amount;

    public IncreaseMemberMoneyEvent() {
    }

    public IncreaseMemberMoneyEvent(String aggregateIdentifier, String targetMembershipId, int amount) {
        this.aggregateIdentifier = aggregateIdentifier;
        this.targetMembershipId = targetMembershipId;
        this.amount = amount;
    }
}
