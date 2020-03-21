package delilah.personal.inumapus.model;

import com.google.gson.annotations.SerializedName;

public class OfficeModel {

    @SerializedName("officeId")
    public int officeId;

    @SerializedName("roomId") // 사무실 호수 - ex.B101,101 등등
    public String roomId;

    @SerializedName("title") // 사무실 이름 - ex.종합상황실
    public String title;

    public OfficeModel(int officeId, String roomId, String title) {
        this.officeId = officeId;
        this.roomId = roomId;
        this.title = title;
    }

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
