package rest.model.entities;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class ShopUnitWithChildren {
    @NotNull(message = "Id can't be null")
    @ApiModelProperty(name = "UUID", example = "3fa85f64-5717-4562-b3fc-2c963f66a444", required = true)
    private UUID id = null;

    @NotBlank(message = "Name can't be blank")
    @ApiModelProperty(name = "name", example = "Оффер", required = true)
    private String name = null;

    @ApiModelProperty(name = "parent UUID", example = "3fa85f64-5717-4562-b3fc-2c963f66a333")
    private UUID parentId = null;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type can't be null")
    private ShopUnitType type = null;

    @PositiveOrZero
    private Long price = null;

    @NotNull
    private ZonedDateTime updateDate = null;

    @ApiModelProperty(name = "category children array", example = "3fa85f64-5717-4562-b3fc-2c963f66a333")
    private ArrayList<ShopUnit> children;

    public ShopUnitWithChildren(){}


    public ShopUnitWithChildren id(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ShopUnitWithChildren name(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShopUnitWithChildren parentId(UUID parentId) {
        this.parentId = parentId;
        return this;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public ShopUnitWithChildren type(ShopUnitType type) {
        this.type = type;
        return this;
    }

    public ShopUnitType getType() {
        return type;
    }

    public void setType(ShopUnitType type) {
        this.type = type;
    }

    public ShopUnitWithChildren price(Long price) {
        this.price = price;
        return this;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ShopUnitWithChildren updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public ArrayList<ShopUnit> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ShopUnit> children) {
        this.children = children;
    }

    public void children(ArrayList<ShopUnit> children){
        this.children = children;
    }

    public void addChildren(ShopUnit unit){
        children.add(unit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShopUnitWithChildren ShopUnit = (ShopUnitWithChildren) o;
        return Objects.equals(this.id, ShopUnit.id) &&
                Objects.equals(this.name, ShopUnit.name) &&
                Objects.equals(this.parentId, ShopUnit.parentId) &&
                Objects.equals(this.type, ShopUnit.type) &&
                Objects.equals(this.price, ShopUnit.price) &&
                Objects.equals(this.updateDate, ShopUnit.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parentId, type, price);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ShopUnitWithChildren {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
