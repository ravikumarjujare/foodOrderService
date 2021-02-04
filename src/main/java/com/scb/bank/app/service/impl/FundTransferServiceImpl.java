package com.scb.bank.app.service.impl;

import com.scb.bank.app.entity.Account;
import com.scb.bank.app.entity.TransactionDetails;
import com.scb.bank.app.exception.TransactionFailureException;
import com.scb.bank.app.meta.ResponseMessageType;
import com.scb.bank.app.meta.TransactionType;
import com.scb.bank.app.repository.AccountRepository;
import com.scb.bank.app.repository.TransactionRepository;
import com.scb.bank.app.service.FundTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class FundTransferServiceImpl implements FundTransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    @Transactional
    public String doFundTransfer(long fromAcc, long toAccNo, int amountToTransfer) {

        Optional<Account> fromAccount = accountRepository.findById(fromAcc);
        Optional<Account> toAccount = accountRepository.findById(toAccNo);

        if (fromAccount.isPresent() && toAccount.isPresent()) {
            if (!fromAccount.get().getId().equals(toAccount.get().getId())) {
                if (fromAccount.get().getAccountBalance() != 0 && fromAccount.get().getAccountBalance() >= amountToTransfer) {
                    //debit from account
                    try {
                        long fromClosingBalance=fromAccount.get().getAccountBalance() - amountToTransfer;
                        fromAccount.get().setAccountBalance(fromAccount.get().getAccountBalance() - amountToTransfer);
                        accountRepository.save(fromAccount.get());
                        TransactionDetails fromTransaction = new TransactionDetails();
                        fromTransaction.setTransactDate(LocalDate.now());
                        fromTransaction.setTransactAmount(-amountToTransfer);
                        fromTransaction.setTransactType(TransactionType.DEBIT.getTransactionType());
                        fromTransaction.setAccountNumber(fromAccount.get().getId());
                        fromTransaction.setClosingBalance(fromClosingBalance);
                        fromTransaction.setTransactInfo(String.format("Transferred amount to %s",toAccNo));
                        transactionRepository.save(fromTransaction);
                        //credit to account
                        long toClosingBalance=toAccount.get().getAccountBalance() + amountToTransfer;
                        toAccount.get().setAccountBalance(toAccount.get().getAccountBalance() + amountToTransfer);
                        accountRepository.save(toAccount.get());
                        TransactionDetails toTransaction = new TransactionDetails();
                        toTransaction.setTransactDate(LocalDate.now());
                        toTransaction.setTransactAmount(amountToTransfer);
                        toTransaction.setTransactType(TransactionType.CREDIT.getTransactionType());
                        toTransaction.setAccountNumber(toAccount.get().getId());
                        toTransaction.setClosingBalance(toClosingBalance);
                        toTransaction.setTransactInfo(String.format("Credited amount from %s",fromAcc));
                        transactionRepository.save(toTransaction);
                    } catch (RuntimeException ex) {
                        throw new TransactionFailureException();
                    }
                } else {
                    return ResponseMessageType.INSUFFICIENT_ACCOUNT_BALANCE.getType();
                }
            } else {
                return ResponseMessageType.SAME_ACCOUNT_NO.getType();
            }
        } else {
            return ResponseMessageType.ACCOUNT_NOT_FOUNT.getType();
        }
        return ResponseMessageType.TRANSACTION_SUCCEED.getType();
    }
}
