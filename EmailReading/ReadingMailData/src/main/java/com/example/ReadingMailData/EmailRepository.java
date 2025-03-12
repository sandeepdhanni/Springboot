package com.example.ReadingMailData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface EmailRepository extends JpaRepository<EmailEntity, Long> {}
