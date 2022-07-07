package rest.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import rest.model.ShopUnitImportRequest;

import javax.validation.Valid;

public interface ImportsApi {

    @PostMapping("/imports")
    ResponseEntity<Void> importsPost(@Valid @RequestBody ShopUnitImportRequest shopUnitImportRequest, BindingResult bindingResult) throws MethodArgumentNotValidException;
}
