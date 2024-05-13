package com.fastcampuspay.money.adapter.in.axon.command;

import com.fastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class IncreaseMemberMoneyCommand {

    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIndentifier;

    @NotNull
    private final String membershipId;

    @NotNull
    private final int amount;
}