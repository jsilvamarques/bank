package br.com.impacta.bank.account.resource;

import br.com.impacta.bank.account.dto.BankTransactionDto;
import br.com.impacta.bank.account.dto.Withdraw;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TransactionResource {

    List<BankTransactionDto> findAll();
    List<BankTransactionDto> findAllByAccountId(Long accountId);
    BankTransactionDto findById(Long id);
    BankTransactionDto transaction(BankTransactionDto accountDto);
    BankTransactionDto withdraw(Withdraw withdraw);
    void delete(Long id);
}
