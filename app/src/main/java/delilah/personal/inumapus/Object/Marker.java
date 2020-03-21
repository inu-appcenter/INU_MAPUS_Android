package delilah.personal.inumapus.Object;

import java.util.ArrayList;

public class Marker {
    private String buildingId, buildingName;
    private Double lat, log;
    private ArrayList<String> officeList;

    public Marker(String buildingId, String buildingName, Double lat, Double log, ArrayList<String> officeList) {
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.lat = lat;
        this.log = log;
        this.officeList = officeList;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLog() {
        return log;
    }

    public void setLog(Double log) {
        this.log = log;
    }

    public ArrayList<String> getOfficeList() {
        return officeList;
    }

    public void setOfficeList(ArrayList<String> officeList) {
        this.officeList = officeList;
    }
}
