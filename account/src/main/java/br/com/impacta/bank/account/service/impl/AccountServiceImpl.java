package br.com.impacta.bank.account.service.impl;

import br.com.impacta.bank.account.domain.Account;
import br.com.impacta.bank.account.dto.AccountDto;
import br.com.impacta.bank.account.dto.AccountRequest;
import br.com.impacta.bank.account.repository.AccountRepository;
import br.com.impacta.bank.account.service.AccountService;
import brave.Span;
import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private Tracer tracer;

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto create(AccountRequest accountRequest) {
        Span newSpan = tracer.nextSpan().name("Request Account Service - create").start();
        log.debug("Request to create Account : {}", accountRequest);

        var account = mapToDto(
                this.accountRepository.save(
                        new Account(accountRequest.getCustomerId())
                )
        );

        newSpan.finish();
        return account;
    }

    @Override
    public List<AccountDto> findAll() {
        Span newSpan = tracer.nextSpan().name("Request Account Service - findAll").start();
        log.debug("Request to get all Accounts");

        var listAccount = this.accountRepository.findAll()
                .stream()
                .map(AccountServiceImpl::mapToDto)
                .collect(Collectors.toList());

        newSpan.finish();
        return listAccount;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto findById(Long id) {
        Span newSpan = tracer.nextSpan().name("Request Account Service - findById").start();
        log.debug("Request to get Account : {}", id);

        var account = this.accountRepository.findById(id).map(AccountServiceImpl::mapToDto).orElse(null);

        newSpan.finish();
        return account;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> findByCustomerId(Long id){
        Span newSpan = tracer.nextSpan().name("Request Account Service - findByCustomerId").start();
        log.debug("Request to find By CustomerId : {}", id);

        var listAccount = this.accountRepository.findByCustomerId(id)
                .stream().map(AccountServiceImpl::mapToDto)
                .collect(Collectors.toList());

        newSpan.finish();
        return listAccount;
    }

    @Override
    public void delete(Long id) {
        Span newSpan = tracer.nextSpan().name("Request Account Service - delete").start();
        log.debug("Request to delete Account : {}", id);

        Account account = this.accountRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cannot find Customer with id " + id));

        this.accountRepository.delete(account);
        newSpan.finish();
    }

    public static AccountDto mapToDto(Account account) {
        if (account != null) {
            return new AccountDto(
                    account.getId(),
                    account.getCustomerId(),
                    account.getBalance()
            );
        }
        return null;
    }
}
