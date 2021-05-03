package br.com.impacta.bank.account.resource.impl;

import br.com.impacta.bank.account.dto.AccountDto;
import br.com.impacta.bank.account.dto.AccountRequest;
import br.com.impacta.bank.account.resource.AccountResource;
import br.com.impacta.bank.account.service.impl.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import brave.Span;
import brave.Tracer;

@RestController
@RequestMapping("api/accounts")
public class AccountResourceImpl implements AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResourceImpl.class);

    @Autowired
    private Tracer tracer;

    @Autowired
    private AccountServiceImpl accountService;

    @Override
    @GetMapping
    public List<AccountDto> findAll() {
        return this.accountService.findAll();
    }

    @Override
    @GetMapping("/customers/{customerId}")
    public List<AccountDto> findByCustomerId(@PathVariable Long customerId) {
        Span newSpan = tracer.nextSpan().name("Request Account ResourceImpl - findByCustomerId").start();
        log.debug("Request to find by customer Id Customer : {}", customerId);

        var listAccounts = this.accountService.findByCustomerId(customerId);

        newSpan.finish();
        return listAccounts;
    }

    @Override
    @GetMapping("/{id}")
    public AccountDto findById(@PathVariable Long id) {
        Span newSpan = tracer.nextSpan().name("Request Account ResourceImpl - findById").start();
        log.debug("Request to find by Id Account : {}", id);

        AccountDto accountDto = this.accountService.findById(id);

        newSpan.finish();
        return accountDto;
    }

    @Override
    @PostMapping
    public AccountDto create(@RequestBody AccountRequest accountRequest) {
        Span newSpan = tracer.nextSpan().name("Request Account ResourceImpl - create account").start();
        log.debug("Request to create Account : {}", accountRequest);

        var account = this.accountService.create(accountRequest);

        newSpan.finish();
        return account;
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Span newSpan = tracer.nextSpan().name("Request Account ResourceImpl - delete account").start();
        log.debug("Request to delete Account : {}", id);

        this.accountService.delete(id);
        newSpan.finish();
    }
}
