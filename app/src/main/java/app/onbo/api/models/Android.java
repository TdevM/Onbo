package app.onbo.api.models;

public class Android
{
    private String app_version_number;

    private String app_version_code;

    private Features features;

    public String getApp_version_number ()
    {
        return app_version_number;
    }

    public void setApp_version_number (String app_version_number)
    {
        this.app_version_number = app_version_number;
    }

    public String getApp_version_code ()
    {
        return app_version_code;
    }

    public void setApp_version_code (String app_version_code)
    {
        this.app_version_code = app_version_code;
    }

    public Features getFeatures ()
    {
        return features;
    }

    public void setFeatures (Features features)
    {
        this.features = features;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [app_version_number = "+app_version_number+", app_version_code = "+app_version_code+", features = "+features+"]";
    }
}
