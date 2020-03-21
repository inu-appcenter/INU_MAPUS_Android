package delilah.personal.inumapus.Object;

import com.google.gson.annotations.SerializedName;

public class OfficeMain {
    @SerializedName("buildingId")
    private String buildingId;

    @SerializedName("title")
    private String title;

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
