package br.com.impacta.bank.account.resource.impl;

import br.com.impacta.bank.account.dto.AccountDto;
import br.com.impacta.bank.account.resource.AccountResource;
import br.com.impacta.bank.account.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/accounts")
public class AccountResourceImpl implements AccountResource {

    @Autowired
    private AccountServiceImpl accountService;

    @Override
    @GetMapping
    public List<AccountDto> findAll() {
        return this.accountService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public AccountDto findById(@PathVariable Long id) {
        return this.accountService.findById(id);
    }

    @Override
    @PostMapping
    public AccountDto create(AccountDto accountDto) {
        return this.accountService.create(accountDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.accountService.delete(id);
    }
}
