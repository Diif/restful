package rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rest.kafka.Producer;
import rest.model.entities.ShopUnit;
import rest.model.entities.ShopUnitType;
import rest.model.entities.ShopUnitWithChildren;
import rest.model.exceptions.NotFoundException;
import rest.repositories.ShopUnitRepository;
import rest.service.NodesService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static rest.config.CustomUtilities.GET_STAT_SIZE;

@Service
public class NodesServiceImpl implements NodesService {

    private ShopUnitRepository repo;
    private Producer kafkaProducer;

    private final HashMap<UUID, Integer> getStat = new HashMap<>();
    private int counter = 0;

    @Autowired
    public void setRepo(ShopUnitRepository repo, Producer producer) {
        this.repo = repo;
        kafkaProducer =producer;
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
            UUID unitId = unit.getId();
            if(unitId.equals(id)){
                responseUnit
                        .id(unitId)
                        .name(unit.getName())
                        .parentId(unit.getParentId())
                        .price(unit.getPrice())
                        .updateDate(unit.getUpdateDate())
                        .type(unit.getType());
            } else {
                children.add(unit);
            }

            getStat.merge(unitId,1, Integer::sum);
            counter++;
        }
        if(responseUnit.getType() == ShopUnitType.OFFER)
        {
            responseUnit.setChildren(null);
        } else
        {
            responseUnit.setChildren(children);
        }
//        if(counter >= GET_STAT_SIZE){
//            kafkaProducer.sendMessage(getStat);
//            counter = 0;
//            getStat.clear();
//        }
        return new ResponseEntity<ShopUnitWithChildren>(responseUnit, HttpStatus.OK);
    }
}
