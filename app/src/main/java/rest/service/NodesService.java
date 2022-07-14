package rest.service;

import org.springframework.http.ResponseEntity;
import rest.model.ShopUnit;
import rest.model.ShopUnitWithChildren;
import rest.model.exception.NotFoundException;

import java.util.UUID;

public interface NodesService {
    ResponseEntity<ShopUnitWithChildren> nodesGet(UUID id) throws NotFoundException;
}
