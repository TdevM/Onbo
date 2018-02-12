package tdevm.app_ui.api.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tridev on 12-02-2018.
 */

public class UserApp {

    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_gender")
    @Expose
    private Object userGender;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_mobile")
    @Expose
    private String userMobile;
    @SerializedName("user_username")
    @Expose
    private Object userUsername;
    @SerializedName("user_is_verified_email")
    @Expose
    private Boolean userIsVerifiedEmail;
    @SerializedName("user_is_verified_mobile")
    @Expose
    private Boolean userIsVerifiedMobile;
    @SerializedName("user_image_url")
    @Expose
    private String userImageUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getUserGender() {
        return userGender;
    }

    public void setUserGender(Object userGender) {
        this.userGender = userGender;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public Object getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(Object userUsername) {
        this.userUsername = userUsername;
    }

    public Boolean getUserIsVerifiedEmail() {
        return userIsVerifiedEmail;
    }

    public void setUserIsVerifiedEmail(Boolean userIsVerifiedEmail) {
        this.userIsVerifiedEmail = userIsVerifiedEmail;
    }

    public Boolean getUserIsVerifiedMobile() {
        return userIsVerifiedMobile;
    }

    public void setUserIsVerifiedMobile(Boolean userIsVerifiedMobile) {
        this.userIsVerifiedMobile = userIsVerifiedMobile;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

}