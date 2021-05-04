package br.com.impacta.bank.account.service;

import br.com.impacta.bank.account.domain.BankTransaction;
import br.com.impacta.bank.account.dto.BankTransactionDto;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    BankTransactionDto create(BankTransactionDto transactionDto);
    BankTransactionDto withdraw(Long id, BigDecimal amount);
    List<BankTransactionDto> findAll();
    BankTransactionDto findById(Long id);
    void delete(Long id);
    List<BankTransactionDto> findAllByAccountId(Long accountId);

}
