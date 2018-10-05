package tdevm.app_ui.api.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tridev on 12-02-2018.
 */

public class UserApp implements Parcelable {

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

    protected UserApp(Parcel in) {
        userName = in.readString();
        userEmail = in.readString();
        userMobile = in.readString();
        byte tmpUserIsVerifiedEmail = in.readByte();
        userIsVerifiedEmail = tmpUserIsVerifiedEmail == 0 ? null : tmpUserIsVerifiedEmail == 1;
        byte tmpUserIsVerifiedMobile = in.readByte();
        userIsVerifiedMobile = tmpUserIsVerifiedMobile == 0 ? null : tmpUserIsVerifiedMobile == 1;
        userImageUrl = in.readString();
    }

    public static final Creator<UserApp> CREATOR = new Creator<UserApp>() {
        @Override
        public UserApp createFromParcel(Parcel in) {
            return new UserApp(in);
        }

        @Override
        public UserApp[] newArray(int size) {
            return new UserApp[size];
        }
    };

    //Update user details
    public UserApp(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

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


    @Override
    public String toString() {
        return "UserApp{" +
                "userName='" + userName + '\'' +
                ", userGender=" + userGender +
                ", userEmail='" + userEmail + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userUsername=" + userUsername +
                ", userIsVerifiedEmail=" + userIsVerifiedEmail +
                ", userIsVerifiedMobile=" + userIsVerifiedMobile +
                ", userImageUrl='" + userImageUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userEmail);
        dest.writeString(userMobile);
        dest.writeByte((byte) (userIsVerifiedEmail == null ? 0 : userIsVerifiedEmail ? 1 : 2));
        dest.writeByte((byte) (userIsVerifiedMobile == null ? 0 : userIsVerifiedMobile ? 1 : 2));
        dest.writeString(userImageUrl);
    }
}