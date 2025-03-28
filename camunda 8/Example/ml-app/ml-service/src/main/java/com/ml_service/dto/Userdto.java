package com.ml_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Userdto {

    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String phoneNumber;
    private String role;


}
