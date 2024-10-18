package com.example.camunda8_api.s.service;

import com.example.camunda8_api.s.model.AssignTask;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TaskService {


    private final WebClient webClient;

    public TaskService(WebClient webClient) {
        this.webClient = webClient;
    }

//    public Object getAssignTask(AssignTask assignTask)
//    {
//        Object claimed = webClient.patch()
//
//                .uri("/tasks/2251799815445135/assign")
//                .body(Mono.just(assignTask), assignTask.getClass())
//                .retrieve()
//                .bodyToMono(Object.class)
//                .block();
//        return claimed;
//    }



    // Assign a task
    public Object assignTask(String taskId, AssignTask assignTask) {
        return webClient.patch()
                .uri("/tasks/{taskId}/assign", taskId)
                .body(Mono.just(assignTask), AssignTask.class)
                .retrieve()
                .bodyToMono(Object.class)
                .block(); // Blocking for simplicity
    }

    // Complete a task
    public Object completeTask(String taskId) {
        return webClient.post()
                .uri("/tasks/{taskId}/complete", taskId)
                .retrieve()
                .bodyToMono(Object.class)
                .block(); // Blocking for simplicity
    }

    // Unassign a task
    public Object unassignTask(String taskId) {
        return webClient.patch()
                .uri("/tasks/{taskId}/unassign", taskId)
                .retrieve()
                .bodyToMono(Object.class)
                .block(); // Blocking for simplicity
    }

    // Get task by ID
    public Object getTask(String taskId) {
        return webClient.get()
                .uri("/tasks/{taskId}", taskId)
                .retrieve()
                .bodyToMono(Object.class)
                .block(); // Blocking for simplicity
    }

}
