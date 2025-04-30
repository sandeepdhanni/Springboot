package com.example.SchedulerService.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "mail")
public class MailProperties {
    private String host;
    private int port;
    private String username;
    private String password;
    private List<String> subjects;
}
