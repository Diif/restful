package rest.repositories;

import org.springframework.context.annotation.Conditional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import rest.model.ShopUnit;
import rest.model.ShopUnitImport;

import java.util.UUID;
@Repository
public interface ShopUnitImportRepository extends CrudRepository<ShopUnit, UUID>, ShopUnitImportRepositoryCustom {
}
