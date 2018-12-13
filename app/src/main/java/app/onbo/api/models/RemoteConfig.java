package app.onbo.api.models;

public class RemoteConfig {
    private Android android;

    private Object ios;

    public Android getAndroid() {
        return android;
    }

    public void setAndroid(Android android) {
        this.android = android;
    }


    public Object getIos() {
        return ios;
    }

    public void setIos(Object ios) {
        this.ios = ios;
    }

    @Override
    public String toString() {
        return "ClassPojo [android = " + android + ", ios = " + ios + "]";
    }
}

