package com.fastcampuspay.banking.application.port.in;

import com.fastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterBankAccountCommand extends SelfValidating<RegisterBankAccountCommand> {

    @NotNull
    private final String membershipId;

    @NotNull
    private final String bankName;

    @NotNull
    @NotBlank
    private final String bankAccountNumber;

    private final boolean isValid;

    private final String aggregateIdentifier;

    public RegisterBankAccountCommand(String membershipId, String bankName, String bankAccountNumber, boolean isValid, String aggregateIdentifier) {
        this.membershipId = membershipId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.isValid = isValid;
        this.aggregateIdentifier = aggregateIdentifier;

        this.validateSelf();
    }
}
