package com.camunda.academy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.camunda.academy.handler.OrderHandler;
import com.camunda.academy.handler.PackItemsHandler;
import com.camunda.academy.handler.ProcessPaymentHandler;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

public class OrderApplication {

    private static final Logger logger = LoggerFactory.getLogger(OrderApplication.class);

    // Zeebe Client Credentials
    private static final String ZEEBE_PROPERTIES_PATH = "src/main/resources/application.properties";
    private static String ZEEBE_CLIENT_ID;
    private static String ZEEBE_CLIENT_SECRET;
    private static String ZEEBE_TOKEN_AUDIENCE;
    private static String ZEEBE_REST_ADDRESS;
    private static String ZEEBE_GRPC_ADDRESS;

    // Process instance creation
    private static final String PROCESS_ID = "orderProcess";
    private static final int NUM_INSTANCES = 1; // TOTAL NUMBER OF NEW PROCESS INSTANCES CREATED

    // Worker configuration
    private static final int WORKER_TIMEOUT = 1; // Set the time for how long a job is exclusively assigned for this worker.
        
    public static void main(String[] args) {
        loadProperties();
        final OAuthCredentialsProvider credentialsProvider = new OAuthCredentialsProviderBuilder()
            .audience(ZEEBE_TOKEN_AUDIENCE)
            .clientId(ZEEBE_CLIENT_ID)
            .clientSecret(ZEEBE_CLIENT_SECRET)
            .build();

        try (final ZeebeClient client = ZeebeClient.newClientBuilder()
                .grpcAddress(URI.create(ZEEBE_GRPC_ADDRESS))
                .restAddress(URI.create(ZEEBE_REST_ADDRESS))
                .credentialsProvider(credentialsProvider)
                .build()) {

            // Process Instance creator looper
            startProcessInstances(client, NUM_INSTANCES);

            final JobWorker OrderWorker = client.newWorker()
                .jobType("trackOrderStatus")
                .handler(new OrderHandler())
                .timeout(Duration.ofSeconds(WORKER_TIMEOUT).toMillis())
                .fetchVariables("orderId")
                .open();
            
            final JobWorker ProcessPaymentWorker = client.newWorker()
                .jobType("processPayment")
                .handler(new ProcessPaymentHandler())                
                .fetchVariables("orderId")
                .open();
        
            final JobWorker PackItemsWorker = client.newWorker()
                .jobType("packItems")
                .handler(new PackItemsHandler())                
                .fetchVariables("orderId")
                .open();
             
            // Terminate the worker with an Integer input
            Scanner sc = new Scanner(System.in);
            sc.nextInt();
            sc.close();
            OrderWorker.close();
            ProcessPaymentWorker.close();
            PackItemsWorker.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startProcessInstances(ZeebeClient zeebeClient, int numInstances) {
        logger.info("Starting: " + numInstances + " process instances for process: " + PROCESS_ID);
        for (int i = 0; i < numInstances; i++) {
            FakeRandomizer fakeRandomizer = new FakeRandomizer();
            Map<String, Object> fakeRequest = fakeRandomizer.getRandom();
            logger.info("Generating Order({})",fakeRequest.get("orderId"));
            var event = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(PROCESS_ID)
                .latestVersion()
                .variables(fakeRequest)
                .send()
                .join();
                logger.info("Process instance: {} started", event.getProcessInstanceKey());
        }
        logger.info("Ending: " + numInstances + " instances created for process: " + PROCESS_ID);
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
