package rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rest.model.ShopUnit;
import rest.model.ShopUnitType;
import rest.model.ShopUnitWithChildren;
import rest.model.exception.NotFoundException;
import rest.repositories.ShopUnitRepository;
import rest.service.NodesService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NodesServiceImpl implements NodesService {

    private ShopUnitRepository repo;

    @Autowired
    public void setRepo(ShopUnitRepository repo) {
        this.repo = repo;
    }

    @Override
    public ResponseEntity<ShopUnitWithChildren> nodesGet(UUID id) throws NotFoundException {
        List<ShopUnit> units = repo.getParentWithFirstGeneration(id);

        if(units.isEmpty()){
            throw new NotFoundException(id);
        }

        ShopUnitWithChildren responseUnit = new ShopUnitWithChildren();
        ArrayList<ShopUnit> children = new ArrayList<>();
        for (ShopUnit unit : units){
            if(unit.getId().equals(id)){
                responseUnit
                        .id(unit.getId())
                        .name(unit.getName())
                        .parentId(unit.getParentId())
                        .price(unit.getPrice())
                        .updateDate(unit.getUpdateDate())
                        .type(unit.getType());
            } else {
                children.add(unit);
            }
        }
        if(responseUnit.getType() == ShopUnitType.OFFER)
        {
            responseUnit.setChildren(null);
        } else
        {
            responseUnit.setChildren(children);
        }
        return new ResponseEntity<ShopUnitWithChildren>(responseUnit, HttpStatus.OK);
    }
}
