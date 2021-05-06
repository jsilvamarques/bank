package br.com.impacta.bank.authorizationserver.controller;

import br.com.impacta.bank.authorizationserver.dto.UserDto;
import br.com.impacta.bank.authorizationserver.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping("/user")
    public Principal getCurrentLoggedInUser(Principal user) {
        return user;
    }

    @PostMapping
    @RequestMapping("/user/save")
    public UserDto saveUser(@RequestBody UserDto userDto) {
        return this.userDetailsService.saveUser(userDto);
    }
}
