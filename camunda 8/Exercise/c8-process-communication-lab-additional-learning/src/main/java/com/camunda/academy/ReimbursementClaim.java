package com.camunda.academy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.Scanner;

import com.camunda.academy.handler.NotifyConsultantExpensesReimbursedHandler;
import com.camunda.academy.handler.ReimburseExpensesHandler;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

public class ReimbursementClaim {
		
    
	//Application Details
	private static final int WORKER_TIMEOUT = 10;

	//Process Definition Details	
	private static final String REIMBURSE_EXPENSES_JOB_TYPE = "reimburseExpenses";
	private static final String NOTIFY_CONSULTANT_EXPENSES_REIMBURSED_JOB_TYPE = "notifyRequesterExpensesReimbursed";
	
	//Process Variables	
	
    public static void main(String[] args) throws IOException{
    	
    	//Load credentials from resources/config.properties file
    	Properties properties = loadProperties("src/main/resources/cluster.properties");
    	
    	final String ZEEBE_ADDRESS = properties.getProperty("ZEEBE_ADDRESS");
    	final String ZEEBE_CLIENT_ID = properties.getProperty("ZEEBE_CLIENT_ID");
    	final String ZEEBE_CLIENT_SECRET = properties.getProperty("ZEEBE_CLIENT_SECRET");
    	final String ZEEBE_AUTHORIZATION_SERVER_URL = properties.getProperty("ZEEBE_AUTHORIZATION_SERVER_URL");
    	final String ZEEBE_TOKEN_AUDIENCE = properties.getProperty("ZEEBE_TOKEN_AUDIENCE");
    	
    	
    	final OAuthCredentialsProvider credentialsProvider =
    			new OAuthCredentialsProviderBuilder()
			    	.authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
			        .audience(ZEEBE_TOKEN_AUDIENCE)
			        .clientId(ZEEBE_CLIENT_ID)
			        .clientSecret(ZEEBE_CLIENT_SECRET)
			        .build();
	    	
		try (final ZeebeClient client = ZeebeClient.newClientBuilder()
		            .gatewayAddress(ZEEBE_ADDRESS)
		            .credentialsProvider(credentialsProvider)
		            .build()) {
			
			//Request the Cluster Topology
			System.out.println("Connected to Cluster 1: " + client.newTopologyRequest().send().join());
									
			//Reimbursed expenses
			final JobWorker reimburseExpensesWorker =
				    client.newWorker()
				        .jobType(REIMBURSE_EXPENSES_JOB_TYPE)
				        .handler(new ReimburseExpensesHandler())
				        .timeout(Duration.ofSeconds(WORKER_TIMEOUT).toMillis())
				        .open();
			
			//Notify consultant expenses reimbursed
			final JobWorker notifyEmployeeWorker =
				    client.newWorker()
				        .jobType(NOTIFY_CONSULTANT_EXPENSES_REIMBURSED_JOB_TYPE)
				        .handler(new NotifyConsultantExpensesReimbursedHandler())
				        .timeout(Duration.ofSeconds(WORKER_TIMEOUT).toMillis())
				        .open();
			
			//Wait for the Workers
            Scanner sc = new Scanner(System.in);
            sc.nextInt();
            sc.close();
            reimburseExpensesWorker.close();
            notifyEmployeeWorker.close();
			
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
    
    public static Properties loadProperties(String fileName) throws IOException {
    	InputStream input = new FileInputStream(fileName);
        Properties prop = new Properties();
        prop.load(input);
		return prop;
    }
}
