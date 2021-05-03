package br.com.impacta.bank.account.resource.impl;

import br.com.impacta.bank.account.dto.BankTransactionDto;
import br.com.impacta.bank.account.dto.Withdraw;
import br.com.impacta.bank.account.resource.TransactionResource;
import br.com.impacta.bank.account.service.impl.TransactionServiceImpl;
import brave.Span;
import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/transactions")
public class TransactionResourceImpl implements TransactionResource {

    private final Logger log = LoggerFactory.getLogger(TransactionResourceImpl.class);

    @Autowired
    private Tracer tracer;

    private final TransactionServiceImpl transactionService;

    public TransactionResourceImpl(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<BankTransactionDto> findAll() {
        Span newSpan = tracer.nextSpan().name("Request Transaction - findAll").start();
        log.debug("Request to find all");

        var listTransaction = this.transactionService.findAll();

        newSpan.finish();
        return listTransaction;
    }

    @GetMapping("/{id}")
    public BankTransactionDto findById(@PathVariable Long id) {
        Span newSpan = tracer.nextSpan().name("Request Transaction - findById").start();
        log.debug("Request to find by Id : {}", id);

        var bankTransaction =this.transactionService.findById(id);

        newSpan.finish();
        return bankTransaction;
    }

    @PostMapping
    public BankTransactionDto transaction(@RequestBody BankTransactionDto accountDto) {
        Span newSpan = tracer.nextSpan().name("Request Transaction - transaction").start();
        log.debug("Request to transaction : {}", accountDto);

        var bankTransaction = this.transactionService.create(accountDto);

        newSpan.finish();
        return bankTransaction;
    }

    @PostMapping("/withdraw")
    public BankTransactionDto withdraw(@RequestBody Withdraw withdraw) {
        Span newSpan = tracer.nextSpan().name("Request Transaction - withdraw").start();
        log.debug("Request to withdraw : {}", withdraw);

        var bankTransaction = this.transactionService.withdraw(withdraw.getId(), withdraw.getAmount());

        newSpan.finish();
        return bankTransaction;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Span newSpan = tracer.nextSpan().name("Request Transaction - delete").start();
        log.debug("Request to delete : {}", id);

        this.transactionService.delete(id);
        newSpan.finish();
    }
}
