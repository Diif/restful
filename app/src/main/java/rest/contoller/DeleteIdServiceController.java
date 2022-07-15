package rest.contoller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import rest.model.exceptions.NotFoundException;
import rest.service.DeleteIdService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Api
@RestController
public class DeleteIdServiceController {
    private final DeleteIdService delegate;

    @Autowired
    public DeleteIdServiceController(DeleteIdService service){
        delegate = service;
    }

    @DeleteMapping(value = "/delete/{id}")
    // BUG https://github.com/springfox/springfox/issues/3476
    // Return type must be: ResponseEntity<Void>
    @ResponseStatus(HttpStatus.OK)
    public  void DeleteIdDelete(@PathVariable @Valid @NotBlank UUID id) throws NotFoundException {
        delegate.deleteIdDelete(id);
    }
}
