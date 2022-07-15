package rest.model.dto;

import org.springframework.stereotype.Component;
import rest.model.entities.ShopUnit;
import rest.model.entities.ShopUnitImport;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Component
public class ShopUnitRequestMapper {
    public ArrayList<ShopUnit> map(ShopUnitImportRequest shopUnitImportRequest){
        ZonedDateTime zonedDateTime = shopUnitImportRequest.getUpdateDate();
        ArrayList<ShopUnit> arrayList = new ArrayList<>();
        for(ShopUnitImport shopUnitImport : shopUnitImportRequest.getItems()) {
            arrayList.add(new ShopUnit().id(shopUnitImport.getId())
                    .name(shopUnitImport.getName())
                    .parentId(shopUnitImport.getParentId())
                    .price(shopUnitImport.getPrice())
                    .type(shopUnitImport.getType())
                    .updateDate(zonedDateTime));
        }
        return arrayList;
    }

}
