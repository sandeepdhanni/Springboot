package com.example.Security.repository;

import com.example.Security.entity.MfaChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MfaChallengeRepository extends JpaRepository<MfaChallenge, String> {
    Optional<MfaChallenge> findByMfaIdAndVerifiedFalse(String mfaId);
}
