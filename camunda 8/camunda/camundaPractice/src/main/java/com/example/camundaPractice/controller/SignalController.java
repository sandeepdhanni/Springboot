package com.example.camundaPractice.controller;

import com.example.camundaPractice.service.SignalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/signals")
public class SignalController {

    private final SignalService signalService;

    public SignalController(SignalService signalService) {
        this.signalService = signalService;
    }

    @PostMapping
    public ResponseEntity<String> sendSignal(@RequestBody Map<String, String> payload) {
        String signalName = payload.get("name");
        if (signalName == null || signalName.isEmpty()) {
            return ResponseEntity.badRequest().body("Signal name is required");
        }
        signalService.sendSignal(signalName);
        return ResponseEntity.ok("Signal sent: " + signalName);
    }
}

