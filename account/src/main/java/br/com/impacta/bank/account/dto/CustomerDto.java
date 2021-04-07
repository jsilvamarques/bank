package br.com.impacta.bank.account.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CustomerDto {

    private Long id;

    private String name;

    private String email;

    private String telephone;

    private Set<AccountDto> accounts;

    public CustomerDto(Long id, String name, String email, String telephone, Set<AccountDto> accounts) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.accounts = accounts;
    }

}
