package app.onbo.api.models;

/**
 * Created by Tridev on 18-07-2017.
 */

public class OneTimePassword {
    Long otp;
    Long phone;

    public OneTimePassword(Long otp, Long phone) {
        this.otp = otp;
        this.phone = phone;
    }

    public Long getOtp() {
        return otp;
    }

    public void setOtp(Long otp) {
        this.otp = otp;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}
