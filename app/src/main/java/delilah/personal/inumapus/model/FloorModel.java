package delilah.personal.inumapus.model;

import com.google.gson.annotations.SerializedName;

public class FloorModel {
    @SerializedName("buildingId") // 건물 번호 - ex.18-1
    public String buildingId;

    @SerializedName("buildingName") // 단과대 이름 - ex.복지회관
    public String name;

    @SerializedName("floor") // 최고층
    public int max;

    @SerializedName("isBasement") // 지하실 유무
    public int basement;

    public FloorModel(String buildingId, String name, int max, int basement) {
        this.buildingId = buildingId;
        this.name = name;
        this.max = max;
        this.basement = basement;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getBasement() {
        return basement;
    }

    public void setBasement(int basement) {
        this.basement = basement;
    }
}
