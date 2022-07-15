package rest.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rest.model.entities.ShopUnit;

import java.util.UUID;
@Repository
public interface ShopUnitRepository extends CrudRepository<ShopUnit, UUID>, ShopUnitRepositoryCustom {
}
