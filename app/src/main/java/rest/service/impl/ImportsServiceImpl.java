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

import java.util.*;

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

        ArrayList<ShopUnit> shopUnitsArray = mapper.map(shopUnitImportRequest);
        checkRequest(shopUnitsArray,bindingResult);


        repo.saveAll(shopUnitsArray);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void checkRequest(ArrayList<ShopUnit> shopUnitsArray, BindingResult bindingResult) throws MethodArgumentNotValidException {


        HashMap<UUID, ShopUnit> unitsHashMap = new HashMap<>();
        HashSet<UUID> parentUUIDSet = new HashSet<>();

        for(ShopUnit unit : shopUnitsArray){
            // only 1 uniq id per POST
            UUID unitId = unit.getId();
            if(unitsHashMap.get(unitId) != null){
                throw new MethodArgumentNotValidException(null,bindingResult);
            }

            checkUnitPrice(unit,bindingResult);

            parentUUIDSet.add(unit.getParentId());
            unitsHashMap.put(unitId, unit);
        }
        // only category can be a parent
        checkParentCorrectnessInRequestArray(shopUnitsArray,unitsHashMap,bindingResult);
        checkParentCorrectnessInDataBase(parentUUIDSet, bindingResult);

        //forbid type changing
        checkTypeChange(unitsHashMap,bindingResult);
    }

    private void checkUnitPrice(ShopUnit unit, BindingResult bindingResult) throws MethodArgumentNotValidException {
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

    private void checkTypeChange(HashMap<UUID, ShopUnit> unitHashMap, BindingResult bindingResult) throws MethodArgumentNotValidException {
        Iterable<ShopUnit> updatable = repo.findAllById(unitHashMap.keySet());
        for (ShopUnit oldUnit : updatable){
            ShopUnit newUnit = unitHashMap.get(oldUnit.getId());
            if(oldUnit.getType() != newUnit.getType()){
                throw new MethodArgumentNotValidException(null,bindingResult);
            }
        }
    }

    private void checkParentCorrectnessInDataBase(HashSet<UUID> parentUUIDSet, BindingResult bindingResult) throws MethodArgumentNotValidException {
        Iterable<ShopUnit> parents = repo.findAllById(parentUUIDSet);
        for(ShopUnit parent : parents){
            if(parent.getType() == ShopUnitType.CATEGORY){
                throw new MethodArgumentNotValidException(null,bindingResult);
            }
        }
    }

    private void checkParentCorrectnessInRequestArray(ArrayList<ShopUnit> shopUnits,HashMap<UUID, ShopUnit> unitsMap, BindingResult bindingResult) throws MethodArgumentNotValidException {
        for(ShopUnit unit : shopUnits){
            ShopUnit parent = unitsMap.get(unit.getParentId());
            if(parent != null && parent.getType() == ShopUnitType.OFFER){
                throw new MethodArgumentNotValidException(null,bindingResult);
            }
        }
    }

}
