package br.com.impacta.bank.account.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "bank_transaction")
@Data
public class BankTransaction extends AbstractEntity{

    private BigDecimal amount;

    private TransactionType transactionType;

    @ManyToOne
    private Account account;


    public BankTransaction() {

    }

    public BankTransaction(BigDecimal amount, TransactionType transactionType, Account account) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.account = account;
    }
}
