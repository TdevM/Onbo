package tdevm.app_ui.api.models.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tridev on 06-06-2017.
 */

public class User {

    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("username")
    private String userName;
    @SerializedName("gender")
    private String userGender;
    @SerializedName("user_mobile")
    private long user_mobile;
    @SerializedName(value = "mobile")
    private long mobile;

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    //Constructor to Register User
    public User(String name, String userGender, String email, long mobile, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.userGender = userGender;
    }

    public User(String name, String email, long mobile, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
    }

    public User(String password, long user_mobile) {
        this.password = password;
        this.user_mobile = user_mobile;
    }

    public long getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(long user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
