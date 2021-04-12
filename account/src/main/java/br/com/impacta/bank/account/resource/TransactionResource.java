package br.com.impacta.bank.account.resource;

import br.com.impacta.bank.account.dto.BankTransactionDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TransactionResource {

    List<BankTransactionDto> findAll();
    BankTransactionDto findById(@PathVariable Long id);
    BankTransactionDto deposit(BankTransactionDto accountDto);
    void delete(@PathVariable Long id);
}
