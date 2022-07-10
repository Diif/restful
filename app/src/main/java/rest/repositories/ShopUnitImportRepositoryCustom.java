package rest.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rest.model.ShopUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface ShopUnitImportRepositoryCustom {
    @Query(value = """
                WITH RECURSIVE generation AS (
                    SELECT id,
                        name,
                        parent_id,
                        0 AS generation_number
                    FROM shop_unit
                    WHERE id = ?
                \s
                UNION ALL
                \s
                    SELECT child.id,
                        child.name,
                        child.parent_id,
                        generation_number+1 AS generation_number
                    FROM shop_unit child
                    JOIN generation g
                      ON g.id = child.parent_id
                )
                \s
                SELECT id, name
                FROM generation;""", nativeQuery = true)
    List<ShopUnit> getParentWithChildren(UUID id);
}
