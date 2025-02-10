package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.model.request.ProcessDetails;
import com.example.service.MessageTriggerService;
import io.camunda.zeebe.client.ZeebeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.AccountData;
import com.example.service.AccountDataService;

@RestController
@RequestMapping("/api/accounts")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountDataController {

    @Autowired
    private AccountDataService accountDataService;

    @Autowired
    private ZeebeClient client;

    @Autowired
    private MessageTriggerService messageTriggerService;

    @PostMapping
    public ResponseEntity<AccountData> createAccount(@RequestBody AccountData account) {
        return ResponseEntity.ok(accountDataService.createAccount(account));
    }

    @GetMapping("/get-all-account-data")
    public ResponseEntity<List<AccountData>> getAllAccounts() {
        log.info("getAllAccounts in account controller entered for fetch all data");
        return ResponseEntity.ok(accountDataService.getAllAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AccountData>> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountDataService.getAccountById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountData> updateAccount(@PathVariable Long id, @RequestBody AccountData account) {
        return ResponseEntity.ok(accountDataService.updateAccount(id, account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountDataService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assign-user/{messageName}/{key}/{assinedUserName}")
    public void assininingMethod(@PathVariable("messageName") String messageName,@PathVariable("key") String key,@PathVariable("assinedUserName") String assinedUserName){
        log.info("assininingMethod invoked msgName; {} key; ,{} , user; {} : " ,messageName,key,assinedUserName);
        Map<String,String> map=new HashMap<>();
        map.put("assignedUserName",assinedUserName);
        log.info("map user : {}",map);
        client.newPublishMessageCommand().messageName(messageName).correlationKey(key).variables(map).send().exceptionally(throwable -> {
            throw new RuntimeException("not started !!");
        });
    }

    @PostMapping("/assign/users")
    public String assignUsers(@RequestBody List<ProcessDetails> processDetails){
        log.info("processDetails : {}",processDetails);
        return messageTriggerService.triggerMessage(processDetails);
    }


}
