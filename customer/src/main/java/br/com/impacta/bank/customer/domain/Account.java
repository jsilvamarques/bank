package br.com.impacta.bank.customer.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "account")
@Data
public class Account extends AbstractEntity implements Serializable {

    private Long customerId;

    public Account() {

    }

    public Account(Long customerId) {
        this.customerId = customerId;
    }
}
