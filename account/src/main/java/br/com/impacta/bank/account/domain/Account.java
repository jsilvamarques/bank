package br.com.impacta.bank.account.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
@Data
public class Account extends AbstractEntity implements Serializable {

    private Long customerId;

    @ColumnDefault("0")
    private BigDecimal balance = BigDecimal.ZERO;

    public Account() {

    }

    public Account(Long customerId) {
        this.customerId = customerId;
    }

    public Account(Long customerId, BigDecimal balance) {
        this.customerId = customerId;
        this.balance = balance;
    }
}
