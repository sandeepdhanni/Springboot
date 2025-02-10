package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.GroupTable;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupTable, Long> {

    Optional<GroupTable> findByGroupName(String groupName);
    boolean existsByGroupName(String grouName);

}
