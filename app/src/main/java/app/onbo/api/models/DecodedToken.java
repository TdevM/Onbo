package app.onbo.api.models;

import com.google.android.gms.common.util.Base64Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;

public class DecodedToken {

    private String user_id;
    private String email;
    private String phone;

    private String encoded;

    public DecodedToken(String encodedToken) {
        this.encoded = encodedToken;
    }

    private DecodedToken(String user_id, String email, String phone) {
        this.user_id = user_id;
        this.email = email;
        this.phone = phone;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DecodedToken getDecoded() throws UnsupportedEncodingException {
        String[] pieces = encoded.split("\\.");
        String b64payload = pieces[1];
        String jsonString = new String(Base64Utils.decode(b64payload), "UTF-8");
        return new Gson().fromJson(jsonString, DecodedToken.class);
    }

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}