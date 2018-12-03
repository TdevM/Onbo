package onbo.app.api.models;

public class QRDataRestaurant {

    int mode;
    int table;

    public QRDataRestaurant(int mode, int table) {
        this.mode = mode;
        this.table = table;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }
}
