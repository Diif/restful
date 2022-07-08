package rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import rest.dto.ShopUnitRequestMapper;
import rest.model.ShopUnit;
import rest.service.ImportsService;
import rest.model.ShopUnitImport;
import rest.dto.ShopUnitImportRequest;
import rest.model.ShopUnitType;
import rest.repositories.ShopUnitImportRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImportsServiceImpl implements ImportsService {
    private final ShopUnitImportRepository repo;
    private final ShopUnitRequestMapper mapper;
    @Autowired
    public ImportsServiceImpl(ShopUnitImportRepository repo, ShopUnitRequestMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    //TODO сделать так чтобы не полностью отваливался запрос
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
        ArrayList<ShopUnit> shopUnitsArray = mapper.map(shopUnitImportRequest);
        HashMap<UUID, ShopUnitImport> unitsMap = new HashMap<>();
        for(ShopUnitImport unit : shopUnitImportRequest.getItems()){
            UUID unitId = unit.getId();
            UUID parentId = unit.getParentId();
            //TODO вместо обращений в базу передать в БД, в БД поставить ограничения на поля (constrains читать), или ЛУЧШЕ выборку запросом сделать
            //TODO одним обращением вытаскивать для минимизации нагрузки на БД, далее беком обрабатывать через два метода,
            //TODO много мелких запросов = смерть из-за создания кучи TCP соединений
            Optional<ShopUnitImport> databaseUnit =  repo.findById(unitId);
            Optional<ShopUnitImport> databaseParent =  repo.findById(parentId);
            if(databaseUnit.isPresent()){
                // forbid unit type change
                if(databaseUnit.get().getType() != unit.getType()){
                    throw new MethodArgumentNotValidException(null,bindingResult);
                }
            }
            // only category can be a parent
            if(databaseParent.isPresent() && databaseParent.get().getType() != ShopUnitType.CATEGORY){
                throw new MethodArgumentNotValidException(null,bindingResult);
            }
            // only 1 uniq id per POST
            if(unitsMap.get(unitId) != null){
                throw new MethodArgumentNotValidException(null,bindingResult);
            }
            checkUnitPrice(unit,bindingResult);
            unitsMap.put(unitId, unit);
        }
        // only category can be a parent (check units in array only)
        checkParentCorrectnessInRequestArray(shopUnitImportRequest,unitsMap,bindingResult);
    }

    private void checkUnitPrice(ShopUnitImport unit, BindingResult bindingResult) throws MethodArgumentNotValidException {
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

    private void checkParentCorrectnessInRequestArray(ShopUnitImportRequest shopUnitImportRequest,HashMap<UUID, ShopUnitImport> unitsMap, BindingResult bindingResult) throws MethodArgumentNotValidException {
        for(ShopUnitImport unit : shopUnitImportRequest.getItems()){
            ShopUnitImport parent = unitsMap.get(unit.getParentId());
            if(parent != null && parent.getType() == ShopUnitType.OFFER){
                throw new MethodArgumentNotValidException(null,bindingResult);
            }
        }
    }

}
