package com.ts.BankingApplication.service;

import com.ts.BankingApplication.entity.Account;
import com.ts.BankingApplication.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImple implements AccountService {

    @Autowired
    AccountRepository repo;

    @Override
    public Account createAccount(Account account) {
        Account accountSaved=repo.save(account);
        return accountSaved;
    }

    @Override
    public Account getAccountDetails(long AccountNumber) {
        Optional<Account> account=repo.findById(AccountNumber);
        if(account.isEmpty()){
            throw new RuntimeException("Account does not exist");
        }
        Account account_found=account.get();
        return account_found;
    }

    @Override
    public List<Account> getAllAccountDetails() {
        List<Account> listAccount=repo.findAll();
        return listAccount;
    }

    @Override
    public Account depositAccount(Long accountNumber, Double amount) {
        Optional<Account> account=repo.findById(accountNumber);
        if(account.isEmpty()){
            throw new RuntimeException("Account is not present");
        }
        Account accountPresent=account.get();
        Double totalBalance=accountPresent.getAccountBalance()+amount;
        accountPresent.setAccountBalance(totalBalance);
        repo.save(accountPresent);
        return accountPresent;
    }

    @Override
    public Account withdrawAccount(Long accountNumber, Double amount) {
        Optional<Account> account=repo.findById(accountNumber);
        if(account.isEmpty()){
            throw new RuntimeException("Account is not present");
        }
        Account accountPresent=account.get();
        Double accountBalance=accountPresent.getAccountBalance()-amount;
        accountPresent.setAccountBalance(accountBalance);
        repo.save(accountPresent);
        return accountPresent;
    }

    @Override
    public void closeAccount(Long accountNumber) {
        getAccountDetails(accountNumber);
        repo.deleteById(accountNumber);
    }
}
