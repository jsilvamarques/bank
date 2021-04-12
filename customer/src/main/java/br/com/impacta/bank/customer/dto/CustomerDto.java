package br.com.impacta.bank.customer.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CustomerDto {

    private Long id;

    private String name;

    private String email;

    private String telephone;

    private List<AccountDto> accounts;

    public CustomerDto() {
    }

    public CustomerDto(Long id, String name, String email, String telephone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
    }

    public CustomerDto(Long id, String name, String email, String telephone, List<AccountDto> accounts) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.accounts = accounts;
    }

}
