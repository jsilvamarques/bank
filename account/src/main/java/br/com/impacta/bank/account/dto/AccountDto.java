package br.com.impacta.bank.account.dto;

import lombok.Data;

@Data
public class AccountDto {

    private Long id;

    private Long customerId;

    public AccountDto(Long customerId) {
        this.customerId = customerId;
    }

    public AccountDto(Long id, Long customerId) {
        this.id = id;
        this.customerId = customerId;
    }
}
