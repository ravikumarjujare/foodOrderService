package com.scb.bank.app.controller;

import com.scb.bank.app.service.impl.FundTransferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RestController
public class FundTransferController {
    @Autowired
    private FundTransferServiceImpl fundTransferServiceImpl;

    @PostMapping("fund_transfer")
    public String fundTransfer(@NotBlank @RequestParam long fromAccountNo,
                               @NotBlank @RequestParam long toAccountNo, @Min(value = 1, message = "Amount must be greater then Zero") @RequestParam int amtToTransfer) {
        return fundTransferServiceImpl.doFundTransfer(fromAccountNo, toAccountNo, amtToTransfer);
    }
}
