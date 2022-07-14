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
import rest.model.Error;
import rest.service.ImportsService;
import rest.dto.ShopUnitImportRequest;

import javax.validation.Valid;

@Api
@RestController
public class ImportsServiceController {
    private final ImportsService delegate;
    @Autowired
    public ImportsServiceController(ImportsService service) {
        this.delegate = service;
    }
//TODO убрать жирный док
    @ApiOperation("Add or update products/categories")
    @Operation(summary = "", description = "Импортирует новые товары и/или категории. Товары/категории импортированные повторно обновляют текущие. Изменение типа элемента с товара на категорию или с категории на товар не допускается. Порядок элементов в запросе является произвольным.<ul>" +
            "<li>uuid товара или категории является уникальным среди товаров и категорий</li>" +
            "<li>родителем товара или категории может быть только категория</li>" +
            "<li>принадлежность к категории определяется полем parentId</li>" +
            "<li>товар или категория могут не иметь родителя (при обновлении parentId на null, элемент остается без родителя)</li>" +
            "<li>название элемента не может быть null   - у категорий поле price должно содержать null</li>" +
            "<li>цена товара не может быть null и должна быть больше либо равна нулю.</li>" +
            "<li>при обновлении товара/категории обновленными считаются **все** их параметры</li>" +
            "<li>при обновлении параметров элемента обязательно обновляется поле **date** в соответствии с временем обновления</li>" +
            "<li>в одном запросе не может быть двух элементов с одинаковым id</li>" +
            "<li>дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400.</li></ul>" +
            "Гарантируется, что во входных данных нет циклических зависимостей и поле updateDate монотонно возрастает. Гарантируется, что при проверке передаваемое время кратно секундам. ", tags={ "Базовые задачи" })
    //TODO коды поменять на htttStatus.OK...
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вставка или обновление прошли успешно."),
            @ApiResponse(responseCode = "400", description = "Невалидная схема документа или данные не верны", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
    })
    @PostMapping(value = "/imports", produces = "application/json", consumes = "application/json")
    // BUG https://github.com/springfox/springfox/issues/3476
    // Return type must be: ResponseEntity<Void>
    @ResponseStatus(HttpStatus.OK)
    public void importsPost(@ApiParam("ShopUnitImport array with time and date") @Valid @RequestBody ShopUnitImportRequest shopUnitImportRequest, BindingResult bindingResult) throws MethodArgumentNotValidException{
//        return delegate.importsPost(shopUnitImportRequest,bindingResult);
        delegate.importsPost(shopUnitImportRequest,bindingResult);
    }
}
