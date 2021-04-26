package br.com.impacta.bank.customer.service.impl;

import br.com.impacta.bank.customer.domain.Customer;
import br.com.impacta.bank.customer.dto.AccountDto;
import br.com.impacta.bank.customer.dto.AccountRequest;
import br.com.impacta.bank.customer.dto.CustomerDto;
import br.com.impacta.bank.customer.repository.AccountClient;
import br.com.impacta.bank.customer.repository.CustomerRepository;
import br.com.impacta.bank.customer.service.CustomerService;
import com.google.common.collect.Sets;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    @Autowired
    private AccountClient accountClient;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDto create(CustomerDto customerDto) {
        log.debug("Request to create Customer : {}", customerDto);

        val newCustomer = mapToDto(
            this.customerRepository.save(
                new Customer(
                        customerDto.getName(),
                        customerDto.getEmail(),
                        customerDto.getTelephone()
                )
            )
        );

        newCustomer.setAccounts(List.of(
                        this.accountClient.create(new AccountRequest(newCustomer.getId())))
        );

        return newCustomer;
    }

    @Override
    public List<CustomerDto> findAll() {
        log.debug("Request to get all Customers");

        val customerDtos = this.customerRepository.findAll()
                .stream()
                .map(CustomerServiceImpl::mapToDto)
                .collect(Collectors.toList());

        customerDtos.forEach(c ->
                c.setAccounts(this.accountClient.findByCustomerId(c.getId())));

        return customerDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto findById(Long id) {
        log.debug("Request to get Customer : {}", id);
        var customerDto = this.customerRepository.findById(id)
                .map(CustomerServiceImpl::mapToDto)
                .orElse(null);

        if(customerDto != null)
            customerDto.setAccounts(this.accountClient.findByCustomerId(customerDto.getId()));

        return customerDto;
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
                    customer.getTelephone()
            );
        }
        return null;
    }

}
