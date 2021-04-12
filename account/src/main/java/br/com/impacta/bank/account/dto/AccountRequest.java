package br.com.impacta.bank.account.dto;

import lombok.Data;

@Data
public class AccountRequest {

    private Long customerId;

    public AccountRequest() {

    }

    public AccountRequest(Long customerId) {
        this.customerId = customerId;
    }

}
