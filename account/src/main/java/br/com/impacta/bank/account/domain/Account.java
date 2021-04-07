package br.com.impacta.bank.account.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
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
