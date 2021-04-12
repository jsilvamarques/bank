package br.com.impacta.bank.account.service.impl;

import br.com.impacta.bank.account.domain.Account;
import br.com.impacta.bank.account.domain.BankTransaction;
import br.com.impacta.bank.account.domain.TransactionType;
import br.com.impacta.bank.account.dto.BankTransactionDto;
import br.com.impacta.bank.account.repository.AccountRepository;
import br.com.impacta.bank.account.repository.BankTransactionRepository;
import br.com.impacta.bank.account.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final BankTransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(BankTransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public BankTransactionDto create(BankTransactionDto transactionDto) {
        log.debug("Request to create Transaction from : {} with {}", transactionDto.getAccountID(), transactionDto.getAmount());

        Account account = this.accountRepository.findById(transactionDto.getAccountID())
                .orElseThrow(() -> new IllegalStateException("Cannot find Customer with id " + transactionDto.getAccountID()));
        if(transactionDto.getTransactionType() == TransactionType.Deposit){
            account.setBalance(account.getBalance().add(transactionDto.getAmount()));
        }
        else{
            if (account.getBalance().subtract(transactionDto.getAmount()).equals(new BigDecimal("0"))){
                account.setBalance(account.getBalance().subtract(transactionDto.getAmount()));
            }
            else {
                throw new IllegalStateException("Cannot find Customer with id " + transactionDto.getAmount());
            }
        }

        return mapToDto(
                this.transactionRepository.save(
                        new BankTransaction(
                                transactionDto.getAmount(),
                                transactionDto.getTransactionType(),
                                account)
                )
        );
    }

    @Override
    public BankTransactionDto withdraw(Long id, BigDecimal amount) {
        log.debug("Request to create Transaction withdrow from : {} with {}", id, amount);

        Account account = this.accountRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cannot find Customer with id " + id));

        if (account.getBalance().subtract(amount).equals(new BigDecimal("0"))){
            account.setBalance(account.getBalance().subtract(amount));
        }
        else {
            throw new IllegalStateException("Cannot find Customer with id " + id);
        }

        return mapToDto(
                this.transactionRepository.save(
                        new BankTransaction(
                                amount,
                                TransactionType.Withdrow,
                                account)
                )
        );
    }

    @Override
    public List<BankTransactionDto> findAll() {
        log.debug("Request to get all Transactions");
        return this.transactionRepository.findAll()
                .stream()
                .map(TransactionServiceImpl::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BankTransactionDto findById(Long id) {
        log.debug("Request to get Transaction : {}", id);
        return this.transactionRepository.findById(id).map(TransactionServiceImpl::mapToDto).orElse(null);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transaction : {}", id);

        BankTransaction transaction = this.transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cannot find Transaction with id " + id));

        this.transactionRepository.delete(transaction);
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
