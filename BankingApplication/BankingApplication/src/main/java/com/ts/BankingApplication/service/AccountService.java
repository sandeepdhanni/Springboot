package com.ts.BankingApplication.service;

import com.ts.BankingApplication.entity.Account;

import java.util.List;

public interface AccountService {

    public Account createAccount(Account account);

    public Account getAccountDetails(long AccountNumber);

    public List<Account> getAllAccountDetails();

    public Account depositAccount(Long accountNumber,Double amount);

    public Account withdrawAccount(Long accountNumber,Double amount);

    public void closeAccount(Long AccountNumber);
}
