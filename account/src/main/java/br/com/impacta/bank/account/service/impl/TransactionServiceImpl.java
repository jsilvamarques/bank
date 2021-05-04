package br.com.impacta.bank.account.service.impl;

import br.com.impacta.bank.account.domain.Account;
import br.com.impacta.bank.account.domain.BankTransaction;
import br.com.impacta.bank.account.domain.TransactionType;
import br.com.impacta.bank.account.dto.BankTransactionDto;
import br.com.impacta.bank.account.repository.AccountRepository;
import br.com.impacta.bank.account.repository.BankTransactionRepository;
import br.com.impacta.bank.account.service.TransactionService;
import brave.Span;
import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private Tracer tracer;

    private final BankTransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    public TransactionServiceImpl(BankTransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public BankTransactionDto create(BankTransactionDto transactionDto) {
        Span newSpan = tracer.nextSpan().name("Request Transaction Service - create").start();
        log.debug("Request to create Transaction from : {} with {}", transactionDto.getAccountId(), transactionDto.getAmount());

        Account account = this.accountRepository.findById(transactionDto.getAccountId())
                .orElseThrow(() -> new IllegalStateException("Cannot find Customer with id " + transactionDto.getAccountId()));
        if(transactionDto.getTransactionType() == TransactionType.Deposit){
            account.setBalance(account.getBalance().add(transactionDto.getAmount()));
        }
        else{
            var newAmount = account.getBalance().subtract(transactionDto.getAmount()).toBigIntegerExact().doubleValue();
            var comparete = 0D;

            if (newAmount >= comparete){
                account.setBalance(account.getBalance().subtract(transactionDto.getAmount()));
            } else {
                throw new IllegalStateException("withdrawal amount less than the balance amount");
            }
        }

        var bankTransaction = mapToDto(
                this.transactionRepository.save(
                        new BankTransaction(
                                transactionDto.getAmount(),
                                transactionDto.getTransactionType(),
                                account)
                )
        );

        newSpan.finish();
        return bankTransaction;
    }

    @Override
    public BankTransactionDto withdraw(Long id, BigDecimal amount) {
        Span newSpan = tracer.nextSpan().name("Request Transaction Service - withdraw").start();
        log.debug("Request to create Transaction withdrow from : {} with {}", id, amount);

        Account account = this.accountRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cannot find Customer with id " + id));

        if (account.getBalance().subtract(amount).equals(new BigDecimal("0"))){
            account.setBalance(account.getBalance().subtract(amount));
        }
        else {
            throw new IllegalStateException("Cannot find Customer with id " + id);
        }

        var bankTransaction = mapToDto(
                this.transactionRepository.save(
                        new BankTransaction(
                                amount,
                                TransactionType.Withdrow,
                                account)
                )
        );

        newSpan.finish();
        return bankTransaction;
    }

    @Override
    public List<BankTransactionDto> findAll() {
        Span newSpan = tracer.nextSpan().name("Request Transaction Service - findAll").start();
        log.debug("Request to get all Transactions");

        var listBankTransaction = this.transactionRepository.findAll()
                .stream()
                .map(TransactionServiceImpl::mapToDto)
                .collect(Collectors.toList());

        newSpan.finish();
        return listBankTransaction;
    }

    @Override
    public List<BankTransactionDto> findAllByAccountId(Long accountId) {
        Span newSpan = tracer.nextSpan().name("Request Transaction Service - findAllByAccountId").start();
        log.debug("Request to get all Transactions");

        var listBankTransaction = this.transactionRepository.findByAccountId(accountId)
                .stream()
                .map(TransactionServiceImpl::mapToDto)
                .collect(Collectors.toList());

        newSpan.finish();
        return listBankTransaction;
    }

    @Override
    public BankTransactionDto findById(Long id) {
        Span newSpan = tracer.nextSpan().name("Request Transaction Service - findById").start();
        log.debug("Request to get Transaction : {}", id);

        var bankTransaction = this.transactionRepository.findById(id).map(TransactionServiceImpl::mapToDto).orElse(null);

        newSpan.finish();
        return bankTransaction;
    }

    @Override
    public void delete(Long id) {
        Span newSpan = tracer.nextSpan().name("Request Transaction Service - delete").start();
        log.debug("Request to delete Transaction : {}", id);

        BankTransaction transaction = this.transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cannot find Transaction with id " + id));

        this.transactionRepository.delete(transaction);
        newSpan.finish();
    }

    public static BankTransactionDto mapToDto(BankTransaction transaction) {
        if (transaction != null) {
            return new BankTransactionDto(
                    transaction.getId(),
                    transaction.getAmount(),
                    transaction.getTransactionType(),
                    transaction.getId()
            );
        }
        return null;
    }
}
