package br.com.impacta.bank.customer.repository;

import br.com.impacta.bank.customer.dto.AccountDto;
import br.com.impacta.bank.customer.dto.AccountRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@FeignClient(url="http://localhost:8090/api/accounts", name = "account")
public interface AccountClient {

    @GetMapping
    List<AccountDto> findAll();

    @GetMapping("/customers/{customerId}")
    List<AccountDto> findByCustomerId(@PathVariable Long customerId);

    @GetMapping("/{id}")
    Optional<AccountDto> findById(@PathVariable Long id);

    @PostMapping
    AccountDto create(AccountRequest accountRequest);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);
}
