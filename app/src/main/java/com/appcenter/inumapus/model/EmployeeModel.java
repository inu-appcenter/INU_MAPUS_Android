package com.appcenter.inumapus.model;

import com.google.gson.annotations.SerializedName;

public class EmployeeModel {

    @SerializedName("detailOrgan") // 소속 - ex.총장실
    private String detailOrgan;

    @SerializedName("position") // 직위 - ex.총장
    private String position;

    @SerializedName("name") // 직원 이름 - ex.조동성
    private String name;

    @SerializedName("telephone") // 전화번호
    private String telephone;

    @SerializedName("email") // e-mail
    private String email;

    public EmployeeModel(String detailOrgan, String position, String name, String telephone, String email) {
        this.detailOrgan = detailOrgan;
        this.position = position;
        this.name = name;
        this.telephone = telephone;
        this.email = email;
    }

    public String getDetailOrgan() {
        return detailOrgan;
    }

    public String getPosition() {
        return position;
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

    public String getEmail() {
        return email;
    }
}
