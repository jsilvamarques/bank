package br.com.impacta.bank.customer.resource.impl;

import br.com.impacta.bank.customer.dto.CustomerDto;
import br.com.impacta.bank.customer.resource.CustomerResource;
import br.com.impacta.bank.customer.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers")
public class CustomerResourceImpl implements CustomerResource {

    @Autowired
    private CustomerServiceImpl customerService;

    @Override
    @GetMapping
    public List<CustomerDto> findAll() {
        return this.customerService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public CustomerDto findById(@PathVariable Long id) {
        return this.customerService.findById(id);
    }

    @Override
    @PostMapping
    public CustomerDto create(@RequestBody CustomerDto customerDto) {
        return this.customerService.create(customerDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.customerService.delete(id);
    }
}
