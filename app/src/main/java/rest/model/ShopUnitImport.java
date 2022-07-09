package rest.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.lang.annotation.Inherited;
import java.util.Objects;
import java.util.UUID;
public class ShopUnitImport {
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
@ApiModelProperty(name = "type", example = "OFFER")
  private ShopUnitType type = null;

@PositiveOrZero
@ApiModelProperty(name = "price", example = "234")
  private Long price = null;

  public ShopUnitImport(){}

  public ShopUnitImport id(UUID id) {
    this.id = id;
    return this;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public ShopUnitImport name(String name) {
    this.name = name;
    return this;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ShopUnitImport parentId(UUID parentId) {
    this.parentId = parentId;
    return this;
  }

  public UUID getParentId() {
    return parentId;
  }

  public void setParentId(UUID parentId) {
    this.parentId = parentId;
  }

  public ShopUnitImport type(ShopUnitType type) {
    this.type = type;
    return this;
  }

  public ShopUnitType getType() {
    return type;
  }

  public void setType(ShopUnitType type) {
    this.type = type;
  }

  public ShopUnitImport price(Long price) {
    this.price = price;
    return this;
  }

  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShopUnitImport shopUnitImport = (ShopUnitImport) o;
    return Objects.equals(this.id, shopUnitImport.id) &&
        Objects.equals(this.name, shopUnitImport.name) &&
        Objects.equals(this.parentId, shopUnitImport.parentId) &&
        Objects.equals(this.type, shopUnitImport.type) &&
        Objects.equals(this.price, shopUnitImport.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, parentId, type, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShopUnitImport {\n");
    
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
