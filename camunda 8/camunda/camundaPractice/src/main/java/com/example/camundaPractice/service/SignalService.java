package com.example.camundaPractice.service;

import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.stereotype.Service;

@Service
public class SignalService {
    private final ZeebeClient zeebeClient;

    public SignalService(ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    public void sendSignal(String signalName) {
        zeebeClient.newPublishMessageCommand()
                .messageName(signalName) // The name of your signal
                .correlationKey("") // Correlation key, optional for start signals
                .send()
                .join();
    }
}
