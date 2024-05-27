package com.fastcampuspay.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class CheckRegisteredBankAccountCommand {

    @TargetAggregateIdentifier
    private String aggregateIdentifier; // RegisteredBankAccount에 대한
    private String rechargeRequestId;
    private String membershipId;

    private String checkRegisteredBankAccountId;

    private String bankName;

    private String bankAccountNumber;

    private int amount;
}
