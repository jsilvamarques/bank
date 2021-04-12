package br.com.impacta.bank.account.service.impl;

import br.com.impacta.bank.account.domain.Account;
import br.com.impacta.bank.account.dto.AccountDto;
import br.com.impacta.bank.account.dto.AccountRequest;
import br.com.impacta.bank.account.repository.AccountRepository;
import br.com.impacta.bank.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto create(AccountRequest accountRequest) {
        log.debug("Request to create Account : {}", accountRequest);

        return mapToDto(
                this.accountRepository.save(
                        new Account(accountRequest.getCustomerId())
                )
        );
    }

    @Override
    public List<AccountDto> findAll() {
        log.debug("Request to get all Accounts");
        return this.accountRepository.findAll()
                .stream()
                .map(AccountServiceImpl::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto findById(Long id) {
        log.debug("Request to get Account : {}", id);
        return this.accountRepository.findById(id).map(AccountServiceImpl::mapToDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> findByCustomerId(Long id){
        return this.accountRepository.findByCustomerId(id)
                .stream().map(AccountServiceImpl::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Account : {}", id);
        Account account = this.accountRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cannot find Customer with id " + id));

        this.accountRepository.delete(account);
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
