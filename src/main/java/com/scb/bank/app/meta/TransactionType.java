package com.scb.bank.app.meta;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TransactionType {

    DEBIT("debit"),
    CREDIT("credit");

    private final String transactionType;
}
