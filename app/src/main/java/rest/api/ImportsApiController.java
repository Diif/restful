package rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestController;
import rest.model.ShopUnitImport;
import rest.model.ShopUnitImportRequest;
import rest.model.ShopUnitType;
import rest.repositories.ShopUnitImportRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ImportsApiController implements ImportsApi {
    private ShopUnitImportRepository repo;
    @Autowired
    public void setRepo(ShopUnitImportRepository repo) {
        this.repo = repo;
    }

    public ResponseEntity<Void> importsPost(ShopUnitImportRequest shopUnitImportRequest, BindingResult bindingResult) throws MethodArgumentNotValidException {

        if(bindingResult.hasErrors())
        {
            throw new MethodArgumentNotValidException(null,bindingResult);
        }

        checkRequest(shopUnitImportRequest,bindingResult);


        repo.saveAll(shopUnitImportRequest.getItems());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void checkRequest(ShopUnitImportRequest shopUnitImportRequest, BindingResult bindingResult) throws MethodArgumentNotValidException {
        HashSet<UUID> uuids = new HashSet<>();
        for(ShopUnitImport unit : shopUnitImportRequest.getItems()){
            UUID unitId = unit.getId();
            UUID parentId = unit.getParentId();
            Optional<ShopUnitImport> databaseUnit =  repo.findById(unitId);
            Optional<ShopUnitImport> databaseParent =  repo.findById(parentId);
            if(databaseUnit.isPresent()){
                // forbid type change
                if(databaseUnit.get().getType() != unit.getType()){
                    throw new MethodArgumentNotValidException(null,bindingResult);
                }
            }
            // only category can be a parent
            if(databaseParent.isPresent() && databaseParent.get().getType() != ShopUnitType.CATEGORY){
                throw new MethodArgumentNotValidException(null,bindingResult);
            }
            // only 1 uniq id per POST
            if(uuids.contains(unitId)){
                throw new MethodArgumentNotValidException(null,bindingResult);
            }
            uuids.add(unitId);
        }
    }

    private void checkUnitFields(ShopUnitImport unit, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if(unit.getType() == ShopUnitType.OFFER){
            if(unit.getPrice() == null){
                throw new MethodArgumentNotValidException(null,bindingResult);
            }
        }else {
            if(unit.getPrice() != null){
                throw new MethodArgumentNotValidException(null,bindingResult);
            }
        }
    }

}
