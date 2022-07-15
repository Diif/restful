package rest.model.entities;

public enum ShopUnitType {
    OFFER("OFFER"),
    CATEGORY("CATEGORY");

    private final String value;

    ShopUnitType(String value){
        this.value = value;
    }

    public String toString(){
        return value;
    }
}


