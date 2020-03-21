package delilah.personal.inumapus.network;

import java.util.ArrayList;

import delilah.personal.inumapus.model.BuildingModel;
import delilah.personal.inumapus.model.EmployeeModel;
import delilah.personal.inumapus.model.FilterModel;
import delilah.personal.inumapus.model.FloorModel;
import delilah.personal.inumapus.model.MarkerModel;
import delilah.personal.inumapus.model.OfficeModel;
import delilah.personal.inumapus.model.PhoneModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("icon/phonebook")
    Call<ArrayList<PhoneModel>> getPhoneInfo();

    // 전체 아이콘 선택시 띄워주는 마커 정보
    @GET("icon/all")
    Call<MarkerModel> getAllMarkers();

    // 각 필터 아이콘 선택시 띄워주는 마커 정보 - 휴게실:1, 카페:2, 식당:3, 편의점:4
    @GET("icon/filter")
    Call<ArrayList<FilterModel>> getFilterMarkers(@Query("filterId") int filterId);

    // 건물 리스트
    @GET("category/all")
    Call<ArrayList<BuildingModel>> getBuilding();

    // 층
    @GET("category/send/floorSelect")
    Call<ArrayList<FloorModel>> getFloor(@Query("buildingId") String buildingId);

    // office
    @GET("category/send/officeSelect")
    Call<ArrayList<OfficeModel>> getOffice(@Query("buildingId") String buildingId, @Query("floorId") String floorId);

    // employee
    @GET("category/send/employeeSelect")
    Call<ArrayList<EmployeeModel>> getEmployee(@Query("officeId") int officeId);

    // etc
    @GET("category/send/elseEmployee")
    Call<ArrayList<EmployeeModel>> getEtc(@Query("buildingName") String buildingName);
}