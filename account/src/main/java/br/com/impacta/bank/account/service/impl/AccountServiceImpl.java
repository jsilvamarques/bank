package br.com.impacta.bank.account.service.impl;

import br.com.impacta.bank.account.domain.Account;
import br.com.impacta.bank.account.domain.Customer;
import br.com.impacta.bank.account.dto.AccountDto;
import br.com.impacta.bank.account.repository.AccountRepository;
import br.com.impacta.bank.account.repository.CustomerRepository;
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
    private final CustomerRepository customerRepository;

    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        log.debug("Request to create Account : {}", accountDto);

        Customer customer = this.customerRepository.findById(accountDto.getCustomerId())
                .orElseThrow(() -> new IllegalStateException("Cannot find Customer with id " + accountDto.getCustomerId()));

        return mapToDto(
                this.accountRepository.save(
                        new Account(customer.getId())
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
                    account.getCustomerId()
            );
        }
        return null;
    }

}
