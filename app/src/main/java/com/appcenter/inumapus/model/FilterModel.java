package com.appcenter.inumapus.model;

import com.google.gson.annotations.SerializedName;

public class FilterModel {
    @SerializedName("id") // 숫자
    public int id;

    @SerializedName("title") // 편의시설 이름 - ex.이마트24, 고집, 여학생휴게실
    public String title;

    @SerializedName("buildingId") // 호관 번호 - ex.1호관:1
    public String buildingId;

    @SerializedName("filterId") // 휴게실:1, 카페:2, 식당:3, 편의점:4
    public int filterId;

    @SerializedName("lat")
    public double lat;

    @SerializedName("log")
    public double log;
}
