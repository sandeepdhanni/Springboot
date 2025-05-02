package com.example.Security.service;

import com.example.Security.entity.MfaChallenge;
import com.example.Security.repository.MfaChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MfaService {

    @Autowired
    public MfaChallengeRepository repository;

    public MfaChallenge initiateMfa(String userId) {
        int correctNumber = new Random().nextInt(90) + 10;
        List<Integer> options = generateOptions(correctNumber);
        String mfaId = UUID.randomUUID().toString();

        MfaChallenge challenge = new MfaChallenge();
        challenge.setMfaId(mfaId);
        challenge.setUserId(userId);
        challenge.setCorrectNumber(correctNumber);
        challenge.setOptions(options);
        challenge.setCreatedAt(LocalDateTime.now());
        challenge.setVerified(false);

        repository.save(challenge);
        return challenge;
    }

    public boolean verifyMfa(String mfaId, int selectedNumber) {
        Optional<MfaChallenge> opt = repository.findByMfaIdAndVerifiedFalse(mfaId);
        if (opt.isPresent()) {
            MfaChallenge challenge = opt.get();
            if (challenge.getCorrectNumber() == selectedNumber) {
                challenge.setVerified(true);
                repository.save(challenge);
                return true;
            }
        }
        return false;
    }

    private List<Integer> generateOptions(int correct) {
        Set<Integer> options = new HashSet<>();
        options.add(correct);
        while (options.size() < 3) {
            options.add(new Random().nextInt(90) + 10);
        }
        List<Integer> shuffled = new ArrayList<>(options);
        Collections.shuffle(shuffled);
        return shuffled;
    }
}
