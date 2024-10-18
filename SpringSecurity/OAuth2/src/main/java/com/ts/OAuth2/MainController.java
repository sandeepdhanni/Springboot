package com.ts.OAuth2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    @RequestMapping("/")
    public String home(){
        return "Home";
    }


    @RequestMapping("/user")
    public Principal user(Principal user){
        return user;
    }
}
