package br.com.impacta.bank.account.service.impl;

import br.com.impacta.bank.account.domain.Customer;
import br.com.impacta.bank.account.dto.AccountDto;
import br.com.impacta.bank.account.dto.CustomerDto;
import br.com.impacta.bank.account.repository.AccountRepository;
import br.com.impacta.bank.account.repository.CustomerRepository;
import br.com.impacta.bank.account.service.CustomerService;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;
    private final AccountServiceImpl accountService;


    public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository, AccountServiceImpl accountService) {
        this.customerRepository = customerRepository;
        this.accountService = accountService;
    }

    @Override
    public CustomerDto create(CustomerDto customerDto) {
        log.debug("Request to create Customer : {}", customerDto);

        val newCustomer = mapToDto(
            this.customerRepository.save(
                new Customer(
                        customerDto.getName(),
                        customerDto.getEmail(),
                        customerDto.getName(),
                        Collections.EMPTY_SET
                )
            )
        );

        newCustomer.getAccounts().add(
                this.accountService.create(
                        new AccountDto(newCustomer.getId())
                )
        );

        return newCustomer;
    }

    @Override
    public List<CustomerDto> findAll() {
        log.debug("Request to get all Customers");
        return this.customerRepository.findAll()
                .stream()
                .map(CustomerServiceImpl::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto findById(Long id) {
        log.debug("Request to get Customer : {}", id);
        return this.customerRepository.findById(id).map(CustomerServiceImpl::mapToDto).orElse(null);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);

        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cannot find Customer with id " + id));

        this.customerRepository.delete(customer);
    }

    public static CustomerDto mapToDto(Customer customer) {
        if (customer != null) {
            return new CustomerDto(
                    customer.getId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getTelephone(),
                    customer.getAccounts().stream().map(AccountServiceImpl::mapToDto).collect(Collectors.toSet())
            );
        }
        return null;
    }
}
