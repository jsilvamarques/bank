package br.com.impacta.bank.customer.resource;

import br.com.impacta.bank.customer.dto.CustomerDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@ApiOperation(value = "Account Resource", notes = "Apis responsáveis por controle de contas")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Operação executada com sucesso"),
        @ApiResponse(code = 400, message = "error, verify your request and try again")
})
public interface CustomerResource {

    public List<CustomerDto> findAll();
    public CustomerDto findById(Long id);
    public CustomerDto create(@RequestBody CustomerDto customerDto);
    public void delete(@PathVariable Long id);

}
