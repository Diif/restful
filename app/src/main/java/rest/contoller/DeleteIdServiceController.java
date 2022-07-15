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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import rest.model.entities.Error;
import rest.model.exceptions.NotFoundException;
import rest.service.DeleteIdService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

import static rest.config.CustomUtilities.*;

@Api
@RestController
public class DeleteIdServiceController {
    private final DeleteIdService delegate;

    @Autowired
    public DeleteIdServiceController(DeleteIdService service){
        delegate = service;
    }

    @ApiOperation("Delete unit {id} with all children.")
    @Operation(description = "Удаляет сущность с {id}. Если это категория, то также удаляет все товары в ней, её подкатегории, товары в них и т.д.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_OK, description = "Удаление прошло успешно."),
            @ApiResponse(responseCode = HTTP_BAD_REQUEST, description = "Невалидная схема документа или входные данные не верны.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = HTTP_NOT_FOUND, description = "Категория/товар не найден.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping(value = "/delete/{id}")
    // BUG https://github.com/springfox/springfox/issues/3476
    // Return type must be: ResponseEntity<Void>
    @ResponseStatus(HttpStatus.OK)
    public  void DeleteIdDelete(@ApiParam(name = "Id товара/категории.") @PathVariable @Valid @NotBlank UUID id) throws NotFoundException {
        delegate.deleteIdDelete(id);
    }
}
