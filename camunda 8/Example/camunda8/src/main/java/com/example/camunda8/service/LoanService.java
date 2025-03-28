package com.example.camunda8.service;

import com.example.camunda8.entity.LoanProcess;
import com.example.camunda8.repository.LoanRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ZeebeClient zeebeClient;


//        @JobWorker(type = "check-credit-score", autoComplete = true)
//        public void check(ActivatedJob job) {
//            log.info("job worker :{}", job);
//
//            // Extract variables from job
//            String variablesJson = job.getVariables();
//            log.info("Received variables: " + variablesJson);
//
//            // Convert JSON string to a Map
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                Map<String, Object> variablesMap = objectMapper.readValue(variablesJson, new TypeReference<Map<String, Object>>() {});
//                String mail = (String) variablesMap.get("mail");
//
//                if (mail != null) {
//                    log.info("Checking loan record for email: {}", mail);
//
//                    Optional<LoanProcess> loanProcessOptional = loanRepository.findByMail(mail);
//                    if (loanProcessOptional.isPresent()) {
//                        LoanProcess loanProcess = loanProcessOptional.get();
//                        Long creditScore = loanProcess.getCredit_score();
//
//                        log.info("Credit score for {}: {}", mail, creditScore);
//
//                        // Send response (if required, add logic to send this back to the process)
//                        Map<String, Object> resultVariables = new HashMap<>();
//                        resultVariables.put("creditScore", creditScore);
//                        log.info("result variables "+resultVariables);
//
//                        // Complete the job with the credit score
//                        job.getClient().newCompleteCommand(job.getKey()).variables(resultVariables).send().join();
//                    } else {
//                        log.warn("No loan record found for email: {}", mail);
//                    }
//                } else {
//                    log.warn("Email not provided in job variables");
//                }
//            } catch (Exception e) {
//                log.error("Error processing job variables", e);
//            }
//
//    }




    @JobWorker(type = "check-credit-score", autoComplete = false)
    public void check(JobClient client, ActivatedJob job) {
        log.info("Job worker received: {}", job);

        String variablesJson = job.getVariables();
        log.info("Received variables: {}", variablesJson);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> variablesMap = objectMapper.readValue(variablesJson, new TypeReference<Map<String, Object>>() {});
            String mail = (String) variablesMap.get("mail");

            // **Fix: Check for null or empty email before database query**
            if (mail == null || mail.trim().isEmpty()) {
                log.warn("Email not provided in job variables");

                // Throw BPMN error for missing email
                client.newThrowErrorCommand(job.getKey())
                        .errorCode("MISSING_EMAIL")
                        .errorMessage("Email is missing in process variables")
                        .send()
                        .join();

                log.info("BPMN error thrown: MISSING_EMAIL");
                return;  // Stop further execution
            }

            log.info("Checking loan record for email: {}", mail);

            Optional<LoanProcess> loanProcessOptional = loanRepository.findByMail(mail);
            if (loanProcessOptional.isPresent()) {
                LoanProcess loanProcess = loanProcessOptional.get();
                Long creditScore = loanProcess.getCredit_score();

                log.info("Credit score for {}: {}", mail, creditScore);

                // Send only creditScore as a response
                client.newCompleteCommand(job.getKey())
                        .variables("{\"creditScore\":" + creditScore + "}")
                        .send()
                        .join();

                log.info("Job completed successfully with creditScore: {}", creditScore);
            } else {
                log.warn("No loan record found for email: {}", mail);

                // Throw BPMN error for email not found
                client.newThrowErrorCommand(job.getKey())
                        .errorCode("EMAIL_NOT_FOUND")
                        .errorMessage("No loan record found for email: " + mail)
                        .send()
                        .join();

                log.info("BPMN error thrown: EMAIL_NOT_FOUND");
            }
        } catch (Exception e) {
            log.error("Error processing job variables", e);
        }
    }


}
