package br.com.impacta.bank.customer.resource.impl;

import br.com.impacta.bank.customer.dto.CustomerDto;
import br.com.impacta.bank.customer.resource.CustomerResource;
import br.com.impacta.bank.customer.service.impl.CustomerServiceImpl;
import brave.Span;
import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers")
public class CustomerResourceImpl implements CustomerResource {

    @Autowired
    private Tracer tracer;

    @Autowired
    private CustomerServiceImpl customerService;

    @Override
    @GetMapping
    public List<CustomerDto> findAll() {
        Span newSpan = tracer.nextSpan().name("findAll customers").start();
        List<CustomerDto> customerDtos = this.customerService.findAll();
        newSpan.finish();
        return customerDtos;
    }

    @Override
    @GetMapping("/{id}")
    public CustomerDto findById(@PathVariable Long id) {
        Span newSpan = tracer.nextSpan().name("findById customer").start();
        CustomerDto customerDto = this.customerService.findById(id);
        newSpan.finish();
        return customerDto;
    }

    @Override
    @PostMapping
    public CustomerDto create(@RequestBody CustomerDto customerDto) {
        Span newSpan = tracer.nextSpan().name("create customer").start();
        CustomerDto newCustomerDto = this.customerService.create(customerDto);
        newSpan.finish();
        return newCustomerDto;
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Span newSpan = tracer.nextSpan().name("create delete").start();
        this.customerService.delete(id);
        newSpan.finish();
    }
}
