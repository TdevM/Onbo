package app.onbo.api.models;

public class ItemHash {
    private String itemHash;
    private Integer itemPrice;

    public ItemHash(String itemHash, Integer itemPrice) {
        this.itemHash = itemHash;
        this.itemPrice = itemPrice;
    }

    public String getItemHash() {
        return itemHash;
    }

    public void setItemHash(String itemHash) {
        this.itemHash = itemHash;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }
}
