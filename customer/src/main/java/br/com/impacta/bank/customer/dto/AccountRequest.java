package br.com.impacta.bank.customer.dto;

import lombok.Data;

@Data
public class AccountRequest {

    private Long customerId;

    public AccountRequest(Long customerId) {
        this.customerId = customerId;
    }

}
