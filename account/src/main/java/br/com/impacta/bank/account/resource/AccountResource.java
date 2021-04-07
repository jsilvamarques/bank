package br.com.impacta.bank.account.resource;

import br.com.impacta.bank.account.dto.AccountDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@ApiOperation(value = "Account Resource", notes = "Apis responsáveis por controle de contas")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Operação executada com sucesso"),
        @ApiResponse(code = 400, message = "error, verify your request and try again")
})
public interface AccountResource {
    List<AccountDto> findAll();
    AccountDto findById(Long id);
    AccountDto create(AccountDto accountDto);
    void delete(Long id);
}
