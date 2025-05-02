package com.example.Security.controller;

import com.example.Security.entity.MfaChallenge;
import com.example.Security.service.MfaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mfa")
@CrossOrigin(origins = "http://localhost:3000")
public class MfaController {

    @Autowired
    private MfaService mfaService;

    @PostMapping("/initiate")
    public ResponseEntity<MfaChallenge> initiate(@RequestParam String userId) {
        return ResponseEntity.ok(mfaService.initiateMfa(userId));
    }

    @GetMapping("/options/{mfaId}")
    public ResponseEntity<List<Integer>> getOptions(@PathVariable String mfaId) {
        return mfaService.repository.findById(mfaId)
                .map(ch -> ResponseEntity.ok(ch.getOptions()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String mfaId, @RequestParam int selectedNumber) {
        boolean result = mfaService.verifyMfa(mfaId, selectedNumber);
        return ResponseEntity.ok(result ? "Verified" : "Failed");
    }
}
