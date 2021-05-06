package br.com.impacta.bank.customer.repository;

import br.com.impacta.bank.customer.dto.AccountDto;
import br.com.impacta.bank.customer.dto.AccountRequest;
import br.com.impacta.bank.customer.dto.TokenDto;
import br.com.impacta.bank.customer.dto.UserDto;
import feign.HeaderMap;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(url="http://localhost:9092", name = "authorization-server")
public interface AuthorizationServerClient {

    @PostMapping("/oauth/token?grant_type=password&username={username}&password={password}")
    TokenDto getToken(@RequestHeader Map<String, String> headerMap, @PathVariable String username, @PathVariable String password);


    @PostMapping("/user/save")
    UserDto create(@RequestHeader Map<String, String> headerMap, @RequestBody UserDto userDto);
}
