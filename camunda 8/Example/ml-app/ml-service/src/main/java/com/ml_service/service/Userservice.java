package com.ml_service.service;

import com.ml_service.dto.Userdto;
import com.ml_service.model.LoginResponse;

import java.util.List;

public interface Userservice {

    String registerUser(Userdto userdto);

    LoginResponse generateJwtToken(String email);

    List<Userdto> getAllUsers();


}
