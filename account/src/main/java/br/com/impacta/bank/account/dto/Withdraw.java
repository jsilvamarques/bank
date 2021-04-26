package br.com.impacta.bank.account.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Withdraw {

    private Long id;
    private BigDecimal amount;

    public Withdraw() {
    }

    public Withdraw(Long id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }
}
