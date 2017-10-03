package tdevm.app_ui.api.models;

/**
 * Created by Tridev on 18-07-2017.
 */

public class OneTimePassword {
    String otp;
    String phone;

    public OneTimePassword(String otp, String phone) {
        this.otp = otp;
        this.phone = phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
