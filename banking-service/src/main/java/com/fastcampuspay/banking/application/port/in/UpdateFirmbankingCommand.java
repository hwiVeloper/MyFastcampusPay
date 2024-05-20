package com.fastcampuspay.banking.application.port.in;

import com.fastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateFirmbankingCommand extends SelfValidating<UpdateFirmbankingCommand> {
    @NotNull
    private final String firmbankingAggregateIdentifier;

    private final int firmbankingStatus;

    public UpdateFirmbankingCommand(String firmbankingAggregateIdentifier, int firmbankingStatus) {
        this.firmbankingAggregateIdentifier = firmbankingAggregateIdentifier;
        this.firmbankingStatus = firmbankingStatus;
    }
}
