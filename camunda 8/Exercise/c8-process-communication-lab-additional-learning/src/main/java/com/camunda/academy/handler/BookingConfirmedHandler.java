package com.camunda.academy.handler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

public class BookingConfirmedHandler implements JobHandler{
	
		//Process Definition Details
		private static final String MESSAGE_NAME = "msg_bookingConfirmed";	

	    @Override
	    public void handle(JobClient client, ActivatedJob job) throws Exception {
	    	
	    	//Obtain the Process Variables
	    	final Map<String, Object> inputVariables = job.getVariablesAsMap();
	    	final String travelRequestId = (String) inputVariables.get("travelRequestId");
	    	final String travelDestination = (String) inputVariables.get("travelDestination");
	    	final String travelDate = (String) inputVariables.get("travelDate");
	    	final String travelFlight =  (String) inputVariables.get("travelFlight");
	    	final String travelHotel =  (String) inputVariables.get("travelHotel");
	    	
	    	
	    	
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
		    	
			try (final ZeebeClient consultantClient = ZeebeClient.newClientBuilder()
			            .gatewayAddress(ZEEBE_ADDRESS)
			            .credentialsProvider(credentialsProvider)
			            .build()) {  	
	    	
	    	   	//Build the Message Variables
								
		    	final Map<String, Object> messageVariables = new HashMap<String, Object>();		    	
		    	
		    	messageVariables.put("travelRequestId", travelRequestId);
		    	messageVariables.put("travelDestination", travelDestination);
		    	messageVariables.put("travelDate", travelDate);
		    	messageVariables.put("travelFlight", travelFlight);
		    	messageVariables.put("travelHotel", travelHotel);
		    			    	
		    	//Send the message
		    	consultantClient.newPublishMessageCommand()
		    		.messageName(MESSAGE_NAME)
		    		.correlationKey(travelRequestId)
		    		.variables(messageVariables)
		    		.send()
		    		.join();
		    	
		    	System.out.println(travelRequestId + " Travel Request: Confirmation sent");	
		    	
		    	//Complete the Job
		    	client.newCompleteCommand(job.getKey()).variables(messageVariables).send().join();
			}
	    }
			
		public static Properties loadProperties(String fileName) throws IOException {
	    	InputStream input = new FileInputStream(fileName);
	        Properties prop = new Properties();
	        prop.load(input);
			return prop;
	    }
}
