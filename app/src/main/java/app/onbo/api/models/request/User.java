package app.onbo.api.models.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tridev on 06-06-2017.
 */

public class User implements Parcelable {

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

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        password = in.readString();
        userName = in.readString();
        userGender = in.readString();
        user_mobile = in.readLong();
        mobile = in.readLong();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(userName);
        dest.writeString(userGender);
        dest.writeLong(user_mobile);
        dest.writeLong(mobile);
    }
}
