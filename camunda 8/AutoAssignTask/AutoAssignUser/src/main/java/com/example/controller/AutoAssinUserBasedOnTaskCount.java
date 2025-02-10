package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.AccountData;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AutoAssinUserBasedOnTaskCount {
	
	  @JobWorker(type = "getAcountValue", autoComplete = true)
	public List<AccountData> assignUserInGroup(ActivatedJob job) {
		  
		log.info("entered!!!");
		return null;
	}
	  
	  @Autowired
	  private ZeebeClient client; 
	  
	  
	  @JobWorker(type = "send", autoComplete = true)
		public void sendMessage(ActivatedJob job) {
		  log.info("intered in send method");
			  client.newPublishMessageCommand().messageName("sendMessage").correlationKey("key-1").send().exceptionally(throwable -> {
		            throw new RuntimeException("not started !!");
		        });
//			return null;  
		  }
}
