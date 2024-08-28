package com.example.SpringSecurityJWT.repository;

import com.example.SpringSecurityJWT.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
}
