package com.ts.BankingApplication.controller;

import com.ts.BankingApplication.entity.Account;
import com.ts.BankingApplication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService service;

    @PostMapping("/createAccount")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
         Account createAccount=service.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAccount);
    }

    @GetMapping("/{accountNumber}")
    public Account getAccountByAccountNumber(@PathVariable Long accountNumber){
        Account account=service.getAccountDetails(accountNumber);
        return account;
    }

    @GetMapping("/getallAccounts")
    public List<Account> getAllAccountDetails(){
       List<Account> allAccountDetails=service.getAllAccountDetails();
       return allAccountDetails;
    }

    @PutMapping("/deposit/{accountNumber}/{amount}")
    public Account depositAccount(@PathVariable Long accountNumber,@PathVariable Double amount){
        Account account=service.depositAccount(accountNumber,amount);
        return account;
    }

    @PutMapping("/`withdraw`/{accountNumber}/{amount}")
    public Account withdrawAccount(@PathVariable Long accountNumber,@PathVariable Double amount){
        Account account=service.withdrawAccount(accountNumber,amount);
        return account;
    }

    @DeleteMapping("/delete/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountNumber){
        service.closeAccount(accountNumber);
        return ResponseEntity.ok("Account closed");
    }
}
