package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.AccountData;
@Repository
public interface AccountDataRepository extends JpaRepository<AccountData, Long> {

	AccountData findByInstanceId(String instanceId);
}
