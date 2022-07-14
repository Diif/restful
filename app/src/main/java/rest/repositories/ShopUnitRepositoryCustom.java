package rest.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rest.model.ShopUnit;

import java.util.List;
import java.util.UUID;

@Repository
//TODO убрать хард-код, имени таблицы shop_unit не должно быть
public interface ShopUnitRepositoryCustom {
    @Query(value = """
                WITH RECURSIVE generation AS (
                    SELECT *,
                        0 AS generation_number
                    FROM shop_unit
                    WHERE id = ?
                \s
                UNION ALL
                \s
                    SELECT child.*,
                        generation_number+1 AS generation_number
                    FROM shop_unit child
                    JOIN generation g
                      ON g.id = child.parent_id
                )
                \s
                SELECT id,name,parent_id,price,type,update_date
                FROM generation;"""
            , nativeQuery = true)
    List<ShopUnit> getParentWithAllGenerations(UUID id);

    @Query(value = """
                SELECT * FROM shop_unit
                WHERE parent_id = ?1 OR id = ?1"""
            , nativeQuery = true)
    List<ShopUnit> getParentWithFirstGeneration(UUID id);

    @Query(value = """
                WITH RECURSIVE generation AS (
                    SELECT *,
                        0 AS generation_number
                    FROM shop_unit
                    WHERE id = ?
                \s
                UNION ALL
                \s
                    SELECT child.*,
                        generation_number+1 AS generation_number
                    FROM shop_unit child
                    JOIN generation g
                      ON g.id = child.parent_id
                )
                \s
                SELECT id
                FROM generation"""
            , nativeQuery = true)
    List<UUID> getUUIDsParentWithAllGenerations(UUID id);
}
