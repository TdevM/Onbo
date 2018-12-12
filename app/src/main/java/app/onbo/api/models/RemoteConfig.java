package app.onbo.api.models;

public class RemoteConfig {
    private Android android;

    private String ios;

    public Android getAndroid() {
        return android;
    }

    public void setAndroid(Android android) {
        this.android = android;
    }

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    @Override
    public String toString() {
        return "ClassPojo [android = " + android + ", ios = " + ios + "]";
    }
}

