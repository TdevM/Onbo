package tdevm.app_ui.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tridev on 05-06-2017.
 */

public class State {

    @SerializedName("state_id")
    private int id;

    @SerializedName("state_code")
    private String code;

    @SerializedName("state_name")
    private String name;

    @SerializedName("country_id")
    private int countryId;

    public State(int id, String code, String name, int countryId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.countryId = countryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }


}


