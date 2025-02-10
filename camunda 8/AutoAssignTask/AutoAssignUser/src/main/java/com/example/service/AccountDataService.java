package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.CompleteJobResponse;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.AccountData;
import com.example.repository.AccountDataRepository;

@Service
@Slf4j
public class AccountDataService {

	@Autowired
	private AccountDataRepository accountRepository;

	@Autowired
	private ZeebeClient zeebeClient;

	// Create operation
	public AccountData createAccount(AccountData account) {
		return accountRepository.save(account);
	}

	// Read operation (Get all accounts)
	public List<AccountData> getAllAccounts() {
		log.info("getAllAccounts in account service entered for fetch all data");
		return accountRepository.findAll().stream().filter(u->u.getStatus().equalsIgnoreCase("pending")).toList();
	}

	// Read operation (Get account by ID)
	public Optional<AccountData> getAccountById(Long id) {
		return accountRepository.findById(id);
	}

	// Update operation
	public AccountData updateAccount(Long id, AccountData accountDetails) {
		return accountRepository.findById(id).map(account -> {
			account.setAssignedGroup(accountDetails.getAssignedGroup());
			account.setAccountNumber(accountDetails.getAccountNumber());
			account.setAssignedUser(accountDetails.getAssignedUser());
			return accountRepository.save(account);
		}).orElseThrow(() -> new RuntimeException("Account not found"));
	}

	// Delete operation
	public void deleteAccount(Long id) {
		accountRepository.deleteById(id);
	}




	//saves accountdata requests in db
	@JobWorker(type="store-data",autoComplete = false)
	public void saveAccountRequest(ActivatedJob job){
		log.info("saveAccountRequest entered : {}",job);

		String accountNumber = (String)job.getVariable("accountNumber");
		Object accountValueObj = job.getVariable("accountValue");
		long  accountValue = Long.parseLong(accountValueObj.toString());

		long processInstanceKeyLong = job.getProcessInstanceKey();
		String instanceId = String.valueOf(processInstanceKeyLong);

		String assignedGroupName = (String) job.getVariable("groupId");
		String taskId="Activity_0yz6kfu";
		String status ="pending";

		//creating AccountData obj and set data
		AccountData accountData = new AccountData();
		accountData.setAccountValue(accountValue);
		accountData.setAccountNumber(accountNumber);
		accountData.setInstanceId(instanceId);
		accountData.setAssignedGroup(assignedGroupName);
		accountData.setTaskId(taskId);
		accountData.setStatus(status);
		accountData.setAssignedUser("peding");
		log.info("accountdata : {}",accountData);

		//database calling method invoke for save
		AccountData account = createAccount(accountData);

		Map<String,Object> savedData = new HashMap<>();
		savedData.put("Saved_data",account);

		CompleteJobResponse join = zeebeClient.newCompleteCommand(job.getKey())
				.variables(savedData)
				.send()
				.join();

		log.info("completing job");

	}
}
