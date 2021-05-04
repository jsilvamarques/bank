package br.com.impacta.bank.account.service;

import br.com.impacta.bank.account.dto.AccountDto;
import br.com.impacta.bank.account.dto.AccountRequest;
import br.com.impacta.bank.account.dto.BankTransactionDto;

import java.util.List;

public interface AccountService {

    AccountDto create(AccountRequest accountRequest);
    List<AccountDto> findAll();
    AccountDto findById(Long id);
    List<AccountDto> findByCustomerId(Long id);
    void delete(Long id);


}
