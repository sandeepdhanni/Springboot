package com.ml_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ml_service.entity.UserEntity;
import lombok.Builder;

@Builder
public class LoginResponse {

    @JsonProperty("jwtToken")
    private String jwtToken;
    @JsonProperty("refreshToken")
    private  String refreshToken;
    @JsonProperty("userEntity")
    private UserEntity userEntity;

    public LoginResponse(String jwtToken, String refreshToken, UserEntity userEntity) {
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
        this.userEntity = userEntity;
    }

    public LoginResponse() {

    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }


}
