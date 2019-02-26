package app.onbo.api.models;

import org.json.JSONObject;

import java.util.Map;

public class QRObject {

    String uuid;
    int entity;
    Map<String,String> data;


    public QRObject() {
    }

    public QRObject(String uuid, int entity, Map<String,String> data) {
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

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String,String> data) {
        this.data = data;
    }
}
