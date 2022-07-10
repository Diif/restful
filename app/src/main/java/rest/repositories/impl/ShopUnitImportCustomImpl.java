package rest.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import rest.model.ShopUnit;
import rest.repositories.ShopUnitImportRepository;
import rest.repositories.ShopUnitImportRepositoryCustom;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ShopUnitImportCustomImpl implements ShopUnitImportRepositoryCustom {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<ShopUnit> rowMapper;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setRowMapper(RowMapper<ShopUnit> rowMapper){
        this.rowMapper = rowMapper;
    }

    @Override
    public List<ShopUnit> getParentWithChildren(UUID id){
        String sql = """
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
                FROM generation;""";

        return jdbcTemplate.query(sql,rowMapper, id);
    }

}
