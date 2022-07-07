package rest.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShopUnitImportRequest {
    @Valid
    private ArrayList<ShopUnitImport> items = null;

    @NotNull
    private ZonedDateTime updateDate = null;

    public ShopUnitImportRequest items(ArrayList<ShopUnitImport> items) {
        this.items = items;
        return this;
    }

    public ShopUnitImportRequest addItemsItem(ShopUnitImport itemsItem) {
        if (this.items == null) {
            this.items = new ArrayList<ShopUnitImport>();
        }
        this.items.add(itemsItem);
        return this;
    }

    public List<ShopUnitImport> getItems() {
        return items;
    }

    public void setItems(ArrayList<ShopUnitImport> items) {
        this.items = items;
    }

    public ShopUnitImportRequest updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShopUnitImportRequest shopUnitImportRequest = (ShopUnitImportRequest) o;
        return Objects.equals(this.items, shopUnitImportRequest.items) &&
                Objects.equals(this.updateDate, shopUnitImportRequest.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, updateDate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ShopUnitImportRequest {\n");

        sb.append("    items: ").append(toIndentedString(items)).append("\n");
        sb.append("    updateDate: ").append(toIndentedString(updateDate)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

