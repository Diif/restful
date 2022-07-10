package rest.repositories.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rest.model.ShopUnit;
import rest.model.ShopUnitType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
public class ShopUnitRowMapper implements RowMapper<ShopUnit> {
    @Override
    public ShopUnit mapRow(ResultSet result, int rowNum) throws SQLException {
        ShopUnit unit = new ShopUnit();

        unit.setId(UUID.fromString(result.getString("id")));
        unit.setName(result.getString("name"));
        unit.setParentId(UUID.fromString(result.getString("parent_id")));
        unit.setPrice(result.getLong("price"));
        unit.setType(ShopUnitType.valueOf(result.getString("type")));
        unit.setUpdateDate(ZonedDateTime.parse(result.getString("update_date")));

        return unit;
    }
}
