package rest.dto;

import org.springframework.stereotype.Component;
import rest.model.ShopUnit;
import rest.model.ShopUnitImport;

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
