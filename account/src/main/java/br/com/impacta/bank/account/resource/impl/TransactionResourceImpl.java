package br.com.impacta.bank.account.resource.impl;

import br.com.impacta.bank.account.dto.BankTransactionDto;
import br.com.impacta.bank.account.resource.TransactionResource;
import br.com.impacta.bank.account.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/transactions")
public class TransactionResourceImpl implements TransactionResource {

    private final TransactionServiceImpl transactionService;

    public TransactionResourceImpl(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<BankTransactionDto> findAll() {
        return this.transactionService.findAll();
    }

    @GetMapping("/{id}")
    public BankTransactionDto findById(@PathVariable Long id) {
        return this.transactionService.findById(id);
    }

    @PostMapping
    public BankTransactionDto deposit(@RequestBody BankTransactionDto accountDto) {
        return this.transactionService.create(accountDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.transactionService.delete(id);
    }
}
