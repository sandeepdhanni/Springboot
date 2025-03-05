package com.camunda.academy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import java.util.Scanner;

import com.camunda.academy.handler.BudgetTrackerHandler;
import com.camunda.academy.handler.SearchCustomerHandler;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

public class CMapsApplication {

	// Zeebe Client Credentials
    private static final String ZEEBE_PROPERTIES_PATH = "src/main/resources/application.properties";
    private static String ZEEBE_CLIENT_ID;
    private static String ZEEBE_CLIENT_SECRET;
    private static String ZEEBE_TOKEN_AUDIENCE;
    private static String ZEEBE_REST_ADDRESS;
    private static String ZEEBE_GRPC_ADDRESS;
	public static void main(String[] args) {
        loadProperties();

		final OAuthCredentialsProvider credentialsProvider = new OAuthCredentialsProviderBuilder()
			.authorizationServerUrl("https://login.cloud.camunda.io/oauth/token")
			.audience(ZEEBE_TOKEN_AUDIENCE)
			.clientId(ZEEBE_CLIENT_ID)
			.clientSecret(ZEEBE_CLIENT_SECRET)
			.build();

		try (final ZeebeClient client = ZeebeClient.newClientBuilder()
				.grpcAddress(URI.create(ZEEBE_GRPC_ADDRESS))
	            .restAddress(URI.create(ZEEBE_REST_ADDRESS))
	            .credentialsProvider(credentialsProvider)			
		     	.build()) {
			
			System.out.println("Connected to: " + client.newTopologyRequest().send().join());

			// Start a Job Worker
			final JobWorker SearchCustomerWorker = client.newWorker()
				.jobType("verifyAddress")
				.handler(new SearchCustomerHandler())
				.open();	
							
			final JobWorker BudgetTrackerWorker = client.newWorker()
				.jobType("trackBudgetCode")
				.handler(new BudgetTrackerHandler())
				.open();
 			
			// Terminate the worker with an Integer input
			Scanner sc = new Scanner(System.in);			
			sc.nextInt();
			sc.close();
			SearchCustomerWorker.close();
			BudgetTrackerWorker.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadProperties() {
		Properties properties = new Properties();
		try (FileInputStream input = new FileInputStream(ZEEBE_PROPERTIES_PATH)) {
			properties.load(input); 
			ZEEBE_CLIENT_ID = properties.getProperty("zeebe.client.cloud.clientId");
			ZEEBE_CLIENT_SECRET = properties.getProperty("zeebe.client.cloud.clientSecret");
			ZEEBE_REST_ADDRESS = properties.getProperty("ZEEBE_REST_ADDRESS");
    		ZEEBE_GRPC_ADDRESS = properties.getProperty("ZEEBE_GRPC_ADDRESS");
			ZEEBE_TOKEN_AUDIENCE = properties.getProperty("ZEEBE_TOKEN_AUDIENCE");
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
