package app.onbo.api.models;

public class QRDataRestaurant {

    Integer mode;
    Integer table;

    public QRDataRestaurant(Integer mode, Integer table) {
        this.mode = mode;
        this.table = table;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getTable() {
        return table;
    }

    public void setTable(Integer table) {
        this.table = table;
    }
}
