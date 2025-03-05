package com.camunda.academy.handler;

import java.util.Map;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class NotifyEmployeeTravelPolicyChangedHandler implements JobHandler{

@Override
    public void handle(JobClient client, ActivatedJob job) {
        final Map<String, Object> inputVariables = job.getVariablesAsMap();
    	final String travelRequestId = (String) inputVariables.get("travelRequestId");

        System.out.println(travelRequestId + " Travel Policy changed");

        //Complete the Job
    	client.newCompleteCommand(job.getKey()).send().join();
    }
}