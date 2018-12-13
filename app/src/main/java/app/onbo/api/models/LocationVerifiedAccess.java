package app.onbo.api.models;

public class LocationVerifiedAccess {

    private String enabled_version_number;

    private Boolean enabled;

    private String enabled_version_code;

    public String getEnabled_version_number ()
    {
        return enabled_version_number;
    }

    public void setEnabled_version_number (String enabled_version_number)
    {
        this.enabled_version_number = enabled_version_number;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getEnabled_version_code ()
    {
        return enabled_version_code;
    }

    public void setEnabled_version_code (String enabled_version_code)
    {
        this.enabled_version_code = enabled_version_code;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [enabled_version_number = "+enabled_version_number+", enabled = "+enabled+", enabled_version_code = "+enabled_version_code+"]";
    }

}
