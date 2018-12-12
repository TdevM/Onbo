package app.onbo.api.models;

public class Features
{
    private LocationVerifiedAccess location_verified_access;

    public LocationVerifiedAccess getLocation_verified_access() {
        return location_verified_access;
    }

    public void setLocation_verified_access(LocationVerifiedAccess location_verified_access) {
        this.location_verified_access = location_verified_access;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [location_verified_access = "+location_verified_access+"]";
    }
}

