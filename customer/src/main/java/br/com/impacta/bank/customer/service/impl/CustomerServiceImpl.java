package br.com.impacta.bank.customer.service.impl;

import br.com.impacta.bank.customer.domain.Customer;
import br.com.impacta.bank.customer.dto.*;
import br.com.impacta.bank.customer.repository.AccountClient;
import br.com.impacta.bank.customer.repository.AuthorizationServerClient;
import br.com.impacta.bank.customer.repository.CustomerRepository;
import br.com.impacta.bank.customer.service.CustomerService;
import brave.Span;
import brave.Tracer;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private Tracer tracer;

    private final CustomerRepository customerRepository;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private AuthorizationServerClient authorizationServerClient;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDto create(CustomerDto customerDto) {

        Span newSpan = tracer.nextSpan().name("Request customer service create customers").start();
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

        this.authorizationServerClient.create(
                getHeaderMap(),
                new UserDto(customerDto.getName(), customerDto.getPassword(), customerDto.getEmail())
        );

        newSpan.finish();
        return newCustomer;
    }

    @Override
    public List<CustomerDto> findAll() {

        Span newSpan = tracer.nextSpan().name("Request customer service findAll customers").start();
        log.debug("Request to get all Customers");

        val customerDtos = this.customerRepository.findAll()
                .stream()
                .map(CustomerServiceImpl::mapToDto)
                .collect(Collectors.toList());

        customerDtos.forEach(c ->
                c.setAccounts(this.accountClient.findByCustomerId(c.getId())));

        newSpan.finish();
        return customerDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto findById(Long id) {

        Span newSpan = tracer.nextSpan().name("Request customer service findById customer").start();
        log.debug("Request to get Customer : {}", id);
        var customerDto = this.customerRepository.findById(id)
                .map(CustomerServiceImpl::mapToDto)
                .orElse(null);

        if(customerDto != null)
            customerDto.setAccounts(this.accountClient.findByCustomerId(customerDto.getId()));

        newSpan.finish();
        return customerDto;
    }

    @Override
    public void delete(Long id) {

        Span newSpan = tracer.nextSpan().name("Request customer service delete customer").start();
        log.debug("Request to delete Customer : {}", id);

        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cannot find Customer with id " + id));

        this.customerRepository.delete(customer);
        newSpan.finish();
    }

    public static CustomerDto mapToDto(Customer customer) {
        if (customer != null) {
            return new CustomerDto(
                    customer.getId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getTelephone(),
                    customer.getPassword()
            );
        }
        return null;
    }

    public Map<String, String> getHeaderMap(){
        TokenDto tokenDto = this.authorizationServerClient.getToken(getTokenHeader(), "admin", "123456");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + tokenDto.getAccess_token());
        return headers;
    }

    public Map<String, String> getTokenHeader(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", "Basic Y29kZXJlZjokMmEkMTAkcDlQazBmUU5BUVNlc0k0dnV2S0EwT1phbkREMg==");
        return headers;
    }
}
