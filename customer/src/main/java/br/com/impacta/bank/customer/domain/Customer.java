package br.com.impacta.bank.customer.domain;

import br.com.impacta.bank.customer.dto.AccountDto;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;


@Entity
@Table(name = "customer")
@Data
public class Customer extends AbstractEntity{

    private String name;

    @Email
    private String email;

    private String telephone;

    public Customer() {

    }

    public Customer(String name, @Email String email, String telephone) {
        this.name = name;
        this.email = email;
        this.telephone = telephone;
    }
}
