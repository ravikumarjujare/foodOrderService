package com.scb.bank.app.service;

import com.scb.bank.app.entity.TransactionDetails;

import java.util.Date;
import java.util.List;

public interface StatementGenerationService {

    List<TransactionDetails> getTransactionInfo(long accountNumber,String start, String to);


}
