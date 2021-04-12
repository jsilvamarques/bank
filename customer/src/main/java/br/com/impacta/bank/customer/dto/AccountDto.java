package br.com.impacta.bank.customer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {

    private Long id;

    private Long customerId;

    private BigDecimal balance;

    public AccountDto() {

    }

    public AccountDto(Long id, Long customerId, BigDecimal balance) {
        this.id = id;
        this.customerId = customerId;
        this.balance = balance;
    }
}
