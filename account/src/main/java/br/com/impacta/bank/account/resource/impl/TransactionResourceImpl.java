package br.com.impacta.bank.account.resource.impl;

import br.com.impacta.bank.account.dto.BankTransactionDto;
import br.com.impacta.bank.account.dto.Withdraw;
import br.com.impacta.bank.account.resource.TransactionResource;
import br.com.impacta.bank.account.service.impl.TransactionServiceImpl;
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
    public BankTransactionDto transaction(@RequestBody BankTransactionDto accountDto) {
        return this.transactionService.create(accountDto);
    }

    @PostMapping("/withdraw")
    public BankTransactionDto withdraw(@RequestBody Withdraw withdraw) {
        return this.transactionService.withdraw(withdraw.getId(), withdraw.getAmount());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.transactionService.delete(id);
    }
}
