package br.com.impacta.bank.customer.service;

import br.com.impacta.bank.customer.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    public CustomerDto create(CustomerDto customerDto);
    public List<CustomerDto> findAll();
    public CustomerDto findById(Long id);
    public void delete(Long id);
}
