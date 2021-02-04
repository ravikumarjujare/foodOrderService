package com.scb.bank.app.service.impl;

import com.scb.bank.app.entity.TransactionDetails;
import com.scb.bank.app.exception.DateFormatException;
import com.scb.bank.app.exception.DateRangeException;
import com.scb.bank.app.repository.TransactionRepository;
import com.scb.bank.app.service.StatementGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StatementGenerationServiceImpl implements StatementGenerationService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<TransactionDetails> getTransactionInfo(long accountNumber,String start, String end) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate =null;
        LocalDate endDate =null;
        try {
            startDate = LocalDate.parse(start, dateFormatter);
            endDate = LocalDate.parse(end, dateFormatter);
            endDate.atTime(23, 59, 59);
        } catch(DateTimeParseException ex){
            throw new DateFormatException();
        }

        if((startDate.isBefore(LocalDate.now()) || startDate.isEqual(LocalDate.now()))
                && (endDate.isBefore(LocalDate.now()) || endDate.isEqual(LocalDate.now()))){
            return transactionRepository.getTransactionBetweenDateAndAccountNumber(accountNumber,startDate, endDate);
        } else{
            throw new DateRangeException();
        }

    }
}
