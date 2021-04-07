package br.com.impacta.bank.account.domain;

import br.com.impacta.bank.account.dto.AccountDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

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

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customerId", fetch = FetchType.EAGER)
    private Set<Account> accounts;

    public Customer() {

    }

    public Customer(String name, @Email String email, String telephone, Set<Account> accounts) {
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.accounts = accounts;
    }
}
