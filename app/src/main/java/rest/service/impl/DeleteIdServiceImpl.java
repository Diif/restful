package rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rest.model.exceptions.NotFoundException;
import rest.repositories.ShopUnitRepository;
import rest.service.DeleteIdService;

import java.util.List;
import java.util.UUID;

@Service
public class DeleteIdServiceImpl implements DeleteIdService {
    private final ShopUnitRepository repo;

    @Autowired
    public DeleteIdServiceImpl(ShopUnitRepository repo){
        this.repo = repo;
    }

    @Override
    public ResponseEntity<Void> deleteIdDelete(UUID id) throws NotFoundException{
        List<UUID> uuidsForDelete = repo.getUUIDsParentWithAllGenerations(id);
        if(uuidsForDelete.isEmpty()){
            throw new NotFoundException(id);
        }
        repo.deleteAllById(uuidsForDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
