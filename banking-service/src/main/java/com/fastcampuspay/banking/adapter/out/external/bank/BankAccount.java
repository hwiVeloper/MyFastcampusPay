package com.fastcampuspay.banking.adapter.out.external.bank;

import lombok.Data;

@Data
public class BankAccount {
    private String bankName;
    private String bankAccountNumber;
    private boolean isValid;
}
