
package onbo.app.api.models.response.v2.FOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaxBreakup {

    @SerializedName("CGST")
    @Expose
    private Double cGST;
    @SerializedName("SGST")
    @Expose
    private Double sGST;
    @SerializedName("IGST")
    @Expose
    private Integer iGST;

    public Double getCGST() {
        return cGST;
    }

    public void setCGST(Double cGST) {
        this.cGST = cGST;
    }

    public Double getSGST() {
        return sGST;
    }

    public void setSGST(Double sGST) {
        this.sGST = sGST;
    }

    public Integer getIGST() {
        return iGST;
    }

    public void setIGST(Integer iGST) {
        this.iGST = iGST;
    }

    @Override
    public String toString() {
        return "TaxBreakup{" +
                "cGST=" + cGST +
                ", sGST=" + sGST +
                ", iGST=" + iGST +
                '}';
    }
}
