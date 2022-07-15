package rest.repositories;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rest.model.entities.ShopUnit;

import java.util.List;
import java.util.UUID;

import static rest.config.CustomUtilities.TABLE_NAME;

@Repository
public interface ShopUnitRepositoryCustom {
    @Query(value = """
                WITH RECURSIVE generation AS (
                    SELECT *,
                        0 AS generation_number
                    FROM\040""" + TABLE_NAME + """
                    \nWHERE id = ?
                \s
                UNION ALL
                \s
                    SELECT child.*,
                        generation_number+1 AS generation_number
                    FROM\040""" + TABLE_NAME + """
                     \nchild
                    JOIN generation g
                      ON g.id = child.parent_id
                )
                \s
                SELECT id,name,parent_id,price,type,update_date
                FROM generation;"""
            , nativeQuery = true)
    List<ShopUnit> getParentWithAllGenerations(UUID id);

    @Query(value = """
                SELECT * FROM\040""" + TABLE_NAME + """
                \nWHERE parent_id = ?1 OR id = ?1"""
            , nativeQuery = true)
    List<ShopUnit> getParentWithFirstGeneration(UUID id);

    @Query(value = """
                WITH RECURSIVE generation AS (
                    SELECT *,
                        0 AS generation_number
                    FROM\040""" + TABLE_NAME + """
                    \nWHERE id = ?
                \s
                UNION ALL
                \s
                    SELECT child.*,
                        generation_number+1 AS generation_number
                    FROM\040""" + TABLE_NAME + """
                 child
                    JOIN generation g
                      ON g.id = child.parent_id
                )
                \s
                SELECT Cast(id as varchar)
                FROM generation"""
            , nativeQuery = true)
    List<UUID> getUUIDsParentWithAllGenerations(UUID id);
}
