package app.onbo.api.models.request;

public class RefreshToken {


    private String user_id;
    private String refreshToken;

    public RefreshToken(String user_id, String refreshToken) {
        this.user_id = user_id;
        this.refreshToken = refreshToken;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
