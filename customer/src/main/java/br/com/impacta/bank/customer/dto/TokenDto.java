package br.com.impacta.bank.customer.dto;

import lombok.Data;

@Data
public class TokenDto {
    private String access_token;
    private String token_type;

    public TokenDto() {
    }

    public TokenDto(String access_token, String token_type) {
        this.access_token = access_token;
        this.token_type = token_type;
    }
}
