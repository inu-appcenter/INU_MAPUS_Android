package com.appcenter.inumapus.model;

import com.google.gson.annotations.SerializedName;

public class PhoneModel {
    @SerializedName("id") // 숫자
    private int id;

    @SerializedName("detailOrgan") // 단과대학 이름 - ex.인문대학
    private String detailOrgan;

    @SerializedName("position") // 직위 - ex.총장
    private String position;

    @SerializedName("name") // 사람 이름
    private String name;

    @SerializedName("telephone") // 전화번호
    private String telephone;

    public PhoneModel(String detailOrgan, String position, String name, String telephone) {
        this.detailOrgan = detailOrgan;
        this.position = position;
        this.name = name;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetailOrgan() {
        return detailOrgan;
    }

    public void setDetailOrgan(String detailOrgan) {
        this.detailOrgan = detailOrgan;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
