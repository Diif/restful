package rest.contoller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import rest.model.entities.Error;
import rest.model.entities.ShopUnitWithChildren;
import rest.model.exceptions.NotFoundException;
import rest.service.NodesService;

import java.util.UUID;

//TODO товары подкатегорий и т.д., оказывается, тоже нужно выводить - добавить.
@Api
@RestController
public class NodesServiceController {
    private final NodesService delegate;

    public NodesServiceController(NodesService service){
        delegate = service;
    }

    @ApiOperation("Get offer/category info")
    @Operation(summary = "", description = "Получает данные о товаре/категории по id. В случае категории также предоставляет список дочерних элементов."
    ,tags = "Базовые задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Информация об элементе.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShopUnitWithChildren.class))),
            @ApiResponse(responseCode = "400", description = "Невалидная схема документа или входные данные не верны.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Категория/товар не найден.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class )))
    })
    @GetMapping(value = "/nodes/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ShopUnitWithChildren> nodesGet(@ApiParam(name = "Id товара/категории.") @PathVariable (name = "id")UUID id) throws NotFoundException {
        return delegate.nodesGet(id);
    }
}
