package onbo.app.api.models;

public class QRObjectRestaurant {

    String uuid;
    int entity;
    QRDataRestaurant data;


    public QRObjectRestaurant() {
    }

    public QRObjectRestaurant(String uuid, int entity, QRDataRestaurant data) {
        this.uuid = uuid;
        this.entity = entity;
        this.data = data;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getEntity() {
        return entity;
    }

    public void setEntity(int entity) {
        this.entity = entity;
    }

    public QRDataRestaurant getData() {
        return data;
    }

    public void setData(QRDataRestaurant data) {
        this.data = data;
    }
}
