package rest.contoller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import rest.model.entities.Error;
import rest.service.ImportsService;
import rest.model.dto.ShopUnitImportRequest;

import javax.validation.Valid;

import static rest.config.CustomUtilities.*;

@Api
@RestController
public class ImportsServiceController {
    private final ImportsService delegate;
    @Autowired
    public ImportsServiceController(ImportsService service) {
        this.delegate = service;
    }

    @ApiOperation("Add or update products/categories")
    @Operation(summary = "", description = "Импортирует новые товары и/или категории. Товары/категории импортированные повторно обновляют текущие. Изменение типа элемента с товара на категорию или с категории на товар не допускается.", tags={ "Базовые задачи" })
    //TODO коды поменять на htttStatus.O1K...
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_OK, description = "Вставка или обновление прошли успешно."),
            @ApiResponse(responseCode = HTTP_BAD_REQUEST, description = "Невалидная схема документа или данные не верны", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
    })
    @PostMapping(value = "/imports", produces = "application/json", consumes = "application/json")
    // BUG https://github.com/springfox/springfox/issues/3476
    // Return type must be: ResponseEntity<Void>
    @ResponseStatus(HttpStatus.OK)
    public void importsPost(@ApiParam("ShopUnitImport array with time and date") @Valid @RequestBody ShopUnitImportRequest shopUnitImportRequest, BindingResult bindingResult) throws MethodArgumentNotValidException{
//        return delegate.importsPost(shopUnitImportRequest,bindingResult);
        String test = String.valueOf(HttpStatus.OK.value());
        delegate.importsPost(shopUnitImportRequest,bindingResult);
    }
}
