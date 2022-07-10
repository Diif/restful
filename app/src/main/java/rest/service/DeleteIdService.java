package rest.service;

import org.springframework.http.ResponseEntity;
import rest.model.exception.NotFoundException;

import java.util.UUID;

public interface DeleteIdService {
    ResponseEntity<Void> deleteIdDelete(UUID id) throws NotFoundException;
}
