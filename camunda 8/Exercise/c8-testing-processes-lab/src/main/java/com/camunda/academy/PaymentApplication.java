package com.camunda.academy;

import java.time.Duration;
import java.util.Scanner;

import com.camunda.academy.handlers.CreditCardChargingHandler;
import com.camunda.academy.handlers.CreditDeductionHandler;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

public class PaymentApplication {

    //Zeebe Client Credentials
    private static final String ZEEBE_ADDRESS = "[ZEEBE_ADDRESS]";
    private static final String ZEEBE_CLIENT_ID = "[ZEEBE_CLIENT_ID]";
    private static final String ZEEBE_CLIENT_SECRET = "[ZEEBE_CLIENT_SECRET]";
    private static final String ZEEBE_AUTHORIZATION_SERVER_URL = "[ZEEBE_AUTHORIZATION_SERVER_URL]";
    private static final String ZEEBE_TOKEN_AUDIENCE = "[ZEEBE_TOKEN_AUDIENCE]";

    //Payment Application Details
    private static final int WORKER_TIMEOUT = 10;

    public static void main(String[] args){

        final OAuthCredentialsProvider credentialsProvider =
                new OAuthCredentialsProviderBuilder()
                        .authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
                        .audience(ZEEBE_TOKEN_AUDIENCE)
                        .clientId(ZEEBE_CLIENT_ID)
                        .clientSecret(ZEEBE_CLIENT_SECRET)
                        .build();

        try (final ZeebeClient client =
                     ZeebeClient.newClientBuilder()
                             .gatewayAddress(ZEEBE_ADDRESS)
                             .credentialsProvider(credentialsProvider)
                             .build()) {

            //Start the Credit Deduction Worker
            final JobWorker creditDeductionWorker =
                    client.newWorker()
                            .jobType("credit-deduction")
                            .handler(new CreditDeductionHandler())
                            .timeout(Duration.ofSeconds(WORKER_TIMEOUT).toMillis())
                            .open();

            //Start the Credit Deduction Worker
            final JobWorker creditCardChargingWorker =
                    client.newWorker()
                            .jobType("credit-card-charging")
                            .handler(new CreditCardChargingHandler())
                            .timeout(Duration.ofSeconds(WORKER_TIMEOUT).toMillis())
                            .open();

            //Wait for the Workers
            Scanner sc = new Scanner(System.in);
            sc.nextInt();
            sc.close();
            creditDeductionWorker.close();
            creditCardChargingWorker.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}