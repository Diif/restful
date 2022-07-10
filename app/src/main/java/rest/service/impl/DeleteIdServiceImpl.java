package rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rest.model.ShopUnit;
import rest.model.exception.NotFoundException;
import rest.repositories.ShopUnitImportRepository;
import rest.service.DeleteIdService;

import java.util.List;
import java.util.UUID;

@Service
public class DeleteIdServiceImpl implements DeleteIdService {
    private final ShopUnitImportRepository repo;

    @Autowired
    public DeleteIdServiceImpl(ShopUnitImportRepository repo){
        this.repo = repo;
    }

    @Override
    public ResponseEntity<Void> deleteIdDelete(UUID id) throws NotFoundException{
        List<ShopUnit> parentWithChild = repo.getParentWithChildren(id);
        if(parentWithChild.isEmpty()){
            throw new NotFoundException(id);
        }
        repo.deleteAll(parentWithChild);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
