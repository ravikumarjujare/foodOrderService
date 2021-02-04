package com.scb.bank.app.meta;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseMessageType {
    ACCOUNT_NOT_FOUNT("From/To account number does not exist"),
    INSUFFICIENT_ACCOUNT_BALANCE("Insufficient balance in From Account"),
    TRANSACTION_SUCCEED("Transaction succeeded"),
    USER_REGISTRATION("User registered & Account created successfully"),
    USER_ALREADY_EXIST("User already exist with same pan card number"),
    SAME_ACCOUNT_NO("From & To account number can not be same");
    private final String type;
}
