package delilah.personal.inumapus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import delilah.personal.inumapus.Object.BuildingMain;
import delilah.personal.inumapus.Object.OfficeMain;

public class MarkerModel {
    @SerializedName("building")
    @Expose
    private ArrayList<BuildingMain> building = null;
    @SerializedName("office")
    @Expose
    private ArrayList<OfficeMain> office = null;

    public MarkerModel(ArrayList<BuildingMain> building, ArrayList<OfficeMain> office) {
        this.building = building;
        this.office = office;
    }

    public ArrayList<BuildingMain> getBuilding() {
        return building;
    }

    public void setBuilding(ArrayList<BuildingMain> building) {
        this.building = building;
    }

    public ArrayList<OfficeMain> getOffice() {
        return office;
    }

    public void setOffice(ArrayList<OfficeMain> office) {
        this.office = office;
    }
}
