package br.com.impacta.bank.account.dto;

import br.com.impacta.bank.account.domain.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankTransactionDto {

    private Long id = 0L;

    private BigDecimal amount;

    private TransactionType transactionType;

    private Long accountId;

    public BankTransactionDto() {

    }

    public BankTransactionDto(long id, BigDecimal amount, TransactionType transactionType, Long accountID) {
        this.id = id;
        this.amount = amount;
        this.transactionType = transactionType;
        this.accountId = accountID;
    }

}
