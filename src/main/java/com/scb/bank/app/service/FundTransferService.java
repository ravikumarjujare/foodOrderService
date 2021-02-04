package com.scb.bank.app.service;

public interface FundTransferService {

    String doFundTransfer(long fromAcc, long toAccNo, int amountToTransfer);
}
