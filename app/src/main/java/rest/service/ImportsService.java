package rest.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import rest.dto.ShopUnitImportRequest;

public interface ImportsService {

    ResponseEntity<Void> importsPost(ShopUnitImportRequest shopUnitImportRequest, BindingResult bindingResult) throws MethodArgumentNotValidException;
}
