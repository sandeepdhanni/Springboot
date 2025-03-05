package com.example.camunad_test;

import com.example.camunad_test.handler.CreditCardServiceHandler;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;

@SpringBootApplication
public class CamunadTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamunadTestApplication.class, args);
	}


	public static void test(ActivatedJob job){
		long elementInstanceKey = job.getElementInstanceKey();
		System.out.println(elementInstanceKey);
	}


	private static final String ZEEBE_ADDRESS = "[ZEEBE_ADDRESS]";
	private static final String ZEEBE_CLIENT_ID = "[ZEEBE_CLIENT_ID]";
	private static final String ZEEBE_CLIENT_SECRET = "[ZEEBE_CLIENT_SECRET]";
	private static final String ZEEBE_AUTHORIZATION_SERVER_URL = "[ZEEBE_AUTHORIZATION_SERVER_URL]";
	private static final String ZEEBE_TOKEN_AUDIENCE = "[ZEEBE_TOKEN_AUDIENCE]";

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
		final JobWorker creditCardWorker =
				client.newWorker()
						.jobType("chargeCreditCard")
						.handler(new CreditCardServiceHandler())
						.timeout(Duration.ofSeconds(10).toMillis())
						.open();
		Thread.sleep(10000);
	} catch (Exception e) {
		e.printStackTrace();
	}



}
