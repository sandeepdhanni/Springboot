package com.example.camunda8_api.s.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://dsm-1.tasklist.camunda.io/5a77a8d1-e277-4669-9186-610ffd4ffcdd/v1")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlFVVXdPVFpDUTBVM01qZEVRME0wTkRFelJrUkJORFk0T0RZeE1FRTBSa1pFUlVWRVF6bERNZyJ9.eyJodHRwczovL2NhbXVuZGEuY29tL2NsdXN0ZXJJZCI6IjVhNzdhOGQxLWUyNzctNDY2OS05MTg2LTYxMGZmZDRmZmNkZCIsImh0dHBzOi8vY2FtdW5kYS5jb20vb3JnSWQiOiJhOGZiODg1NS1hZDMwLTQxZTAtYjhjZi0zYWIwNTNlMWNlNmEiLCJodHRwczovL2NhbXVuZGEuY29tL2NsaWVudElkIjoiaFBPfkhoUTFUZktGQ3I0c21mYS50bDFNYWlZR1pDTDMiLCJpc3MiOiJodHRwczovL3dlYmxvZ2luLmNsb3VkLmNhbXVuZGEuaW8vIiwic3ViIjoibXdvOTB0MnIzMTYwN3ozNkJOSDY5dFdGS0JYNTVqMVdAY2xpZW50cyIsImF1ZCI6InRhc2tsaXN0LmNhbXVuZGEuaW8iLCJpYXQiOjE3MjkyNDQzMzIsImV4cCI6MTcyOTMzMDczMiwic2NvcGUiOiI1YTc3YThkMS1lMjc3LTQ2NjktOTE4Ni02MTBmZmQ0ZmZjZGQiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMiLCJhenAiOiJtd285MHQycjMxNjA3ejM2Qk5INjl0V0ZLQlg1NWoxVyJ9.mcFwWjL6jV4BPivitGRWkT_nCOZE0vZFEO7hAK5TPIZUdrp-eajp5KJzCSK6QnJNsn1m-qPryvm-wdi9yI0gPpreFLB3RfuMzCHpifXpVWDzjaHTHVOqRAMgX6NXfvvdwtgZJhcp0LYwmBtZwPtQ7HplxSxlAO3N08rVhJSO2-2sPcF_6mpwpruOCljcx7Qe5P8HqVYvIIIrS2Y6FnoZkoYBTBYG-y2EBmxxMPK84bZroQxtE94lA7Tkp6Aw8AGnd38K4FCFaYvz9VqakX_GwM7NtvC3CTGtsOVhvKKgAtloUciWLrULMfZZW0kiihhIe_1IO5QS9QZxo2t9BLh1yg")
                .build();


    }
}