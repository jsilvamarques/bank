package br.com.impacta.bank.account.service;

import br.com.impacta.bank.account.dto.AccountDto;

import java.util.List;

public interface AccountService {

    public AccountDto create(AccountDto accountDto);
    public List<AccountDto> findAll();
    public AccountDto findById(Long id);
    public List<AccountDto> findByCustomerId(Long id);
    public void delete(Long id);

}
