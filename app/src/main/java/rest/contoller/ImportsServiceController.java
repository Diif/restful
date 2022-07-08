package rest.contoller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rest.service.ImportsService;
import rest.dto.ShopUnitImportRequest;

import javax.validation.Valid;

@RestController
public class ImportsServiceController {
    private final ImportsService delegate;
    @Autowired
    public ImportsServiceController(ImportsService service) {
        this.delegate = service;
    }

    @PostMapping("/imports")
    public ResponseEntity<Void> importsPost(@Valid @RequestBody ShopUnitImportRequest shopUnitImportRequest, BindingResult bindingResult) throws MethodArgumentNotValidException{
        return delegate.importsPost(shopUnitImportRequest,bindingResult);
    }
}
