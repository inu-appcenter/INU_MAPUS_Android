package delilah.personal.inumapus.model;

import com.google.gson.annotations.SerializedName;

public class BuildingModel {
    @SerializedName("id")
    public int id;
    @SerializedName("buildingName") // 건물 이름 - ex.대학본부
    public String name;
    @SerializedName("buildingId") // 호관 번호 - 18-1
    public String number;

    public BuildingModel(int id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
