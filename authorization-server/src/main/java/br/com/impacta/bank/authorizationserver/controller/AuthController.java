package br.com.impacta.bank.authorizationserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class AuthController {

    @RequestMapping("/user")
    public Principal getCurrentLoggedInUser(Principal user) {
        return user;
    }
}
