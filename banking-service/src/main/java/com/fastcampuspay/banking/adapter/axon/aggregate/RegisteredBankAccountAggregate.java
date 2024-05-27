package com.fastcampuspay.banking.adapter.axon.aggregate;

import com.fastcampuspay.banking.adapter.axon.command.CreateRegisteredBankAccountCommand;
import com.fastcampuspay.banking.adapter.axon.event.CreateRegisteredBankAccountEvent;
import com.fastcampuspay.banking.adapter.out.external.bank.BankAccount;
import com.fastcampuspay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.fastcampuspay.banking.application.port.out.RequestBankAccountInfoPort;
import com.fastcampuspay.common.event.CheckRegisteredBankAccountCommand;
import com.fastcampuspay.common.event.CheckedRegisteredBankAccountEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import javax.validation.constraints.NotNull;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate()
public class RegisteredBankAccountAggregate {

    @AggregateIdentifier
    private String id;

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;

    // 생성자 CommandHandler
    @CommandHandler
    public RegisteredBankAccountAggregate(CreateRegisteredBankAccountCommand command) {
        System.out.println("CreateRegisteredBankAccountCommand Command Handler");
        apply(new CreateRegisteredBankAccountEvent(command.getMembershipId(), command.getBankName(), command.getBankAccountNumber()));
    }

    @CommandHandler
    public void handle(@NotNull CheckRegisteredBankAccountCommand command, RequestBankAccountInfoPort bankAccountInfoPort) {
        System.out.println("CheckRegisteredBankAccountCommand Handler");

        // command를 통해 해당 어그리거트(RegisteredBankAccount)가 정상인지를 확인해야한다.
        id = command.getAggregateIdentifier();

        // registered bank account check
        BankAccount account = bankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber()));
        boolean isValidAccount = account.isValid();

        String firmbankingUUID = UUID.randomUUID().toString();

        // CheckRegisteredBankAccountEvent
        apply(new CheckedRegisteredBankAccountEvent(
                command.getRechargeRequestId()
                , command.getCheckRegisteredBankAccountId()
                , command.getMembershipId()
                , isValidAccount
                , command.getAmount()
                , firmbankingUUID
                , account.getBankName()
                , account.getBankAccountNumber()
        ));
    }

    @EventSourcingHandler
    public void on(CreateRegisteredBankAccountEvent event) {
        System.out.println("CreateRegisteredBankAccountEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = event.getMembershipId();
        bankName = event.getBankName();
        bankAccountNumber = event.getBankAccountNumber();
    }

    public RegisteredBankAccountAggregate() {}
}
