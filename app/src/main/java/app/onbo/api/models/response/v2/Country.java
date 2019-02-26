
package app.onbo.api.models.response.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("alpha_2")
    @Expose
    private String alpha2;
    @SerializedName("alpha_3")
    @Expose
    private String alpha3;
    @SerializedName("country_code")
    @Expose
    private Integer countryCode;
    @SerializedName("iso_3166_2")
    @Expose
    private String iso31662;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("sub_region")
    @Expose
    private String subRegion;
    @SerializedName("region_code")
    @Expose
    private Integer regionCode;
    @SerializedName("sub_region_code")
    @Expose
    private Integer subRegionCode;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getAlpha2() {
        return alpha2;
    }

    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }

    public String getAlpha3() {
        return alpha3;
    }

    public void setAlpha3(String alpha3) {
        this.alpha3 = alpha3;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public String getIso31662() {
        return iso31662;
    }

    public void setIso31662(String iso31662) {
        this.iso31662 = iso31662;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
    }

    public Integer getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(Integer regionCode) {
        this.regionCode = regionCode;
    }

    public Integer getSubRegionCode() {
        return subRegionCode;
    }

    public void setSubRegionCode(Integer subRegionCode) {
        this.subRegionCode = subRegionCode;
    }

}
