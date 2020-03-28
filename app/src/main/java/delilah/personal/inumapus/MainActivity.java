package delilah.personal.inumapus;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import delilah.personal.inumapus.Object.BuildingMain;
import delilah.personal.inumapus.Object.Marker;
import delilah.personal.inumapus.Object.OfficeMain;
import delilah.personal.inumapus.model.FilterModel;
import delilah.personal.inumapus.model.MarkerModel;
import delilah.personal.inumapus.network.NetworkController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {
    private static final String LOG_TAG = "MainActivity";

    private MapView mapView;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};

    private Animation fabOpen, fabClose;
    private Boolean isFabOpen = false;
    private FloatingActionButton btnCall, btnLocation, fab, fab1, fab3, fab4, fab5, fab6;
    private TextView search;

    private boolean addedAll, addedRetire, addedCafe, addedRest, addedConv, addedMarker = false;
    private boolean curLocState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckFirstExecute();

        declaration();

        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        } else {
            checkRunTimePermission();
        }

        clickListenerSetting();
    }

    // 앱 최초 실행 체크 (true : 최초 실행)
    public void CheckFirstExecute() {
        SharedPreferences execute = getSharedPreferences("IsFirst", Activity.MODE_PRIVATE);
        boolean isFirst = execute.getBoolean("isFirst", false);
        if (!isFirst) { //최초 실행시 true 저장
            SharedPreferences.Editor editor = execute.edit();
            editor.putBoolean("isFirst", true);
            editor.apply();
            Intent intent = new Intent(MainActivity.this, FirstActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapView.setShowCurrentLocationMarker(curLocState);
    }

    private void declaration() {
        search = findViewById(R.id.search);

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        btnLocation = findViewById(R.id.buttonLocation);
        btnCall = findViewById(R.id.buttonCall);
        fab = findViewById(R.id.buttonFilter);
        fab1 = findViewById(R.id.buttonRetire);
        fab3 = findViewById(R.id.buttonCafe);
        fab4 = findViewById(R.id.buttonRest);
        fab5 = findViewById(R.id.buttonConv);
        fab6 = findViewById(R.id.buttonAll);

        mapView = findViewById(R.id.mapView);

        mapView.setCustomCurrentLocationMarkerTrackingImage(R.drawable.ic_mylocation, new MapPOIItem.ImageOffset(46, 55));
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.375, 126.633), true);
    }

    private void clickListenerSetting() {
        // 검색바
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BuildingActivity.class);
                startActivity(intent);
            }
        });

        // 현위치 버튼
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!curLocState) {
                    // 현위치 기능이 꺼져 있을 때 - 켜기
                    mapView.setCurrentLocationEventListener(MainActivity.this);
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
                    mapView.setShowCurrentLocationMarker(true);
                    curLocState = true;
                } else {
                    // 현위치 기능이 켜져 있을 때 - 끄기
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                    mapView.setShowCurrentLocationMarker(false);
                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.375, 126.633), true);
                    curLocState = false;
                }
            }
        });

        // 전화번호부 버튼
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhoneBookActivity.class);
                startActivity(intent);
            }
        });

        // 필터 버튼
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabAnimation();
            }
        });

        // 휴게실 버튼
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addedMarker) {
                    // 마커가 표시되어 있지 않을 때
                    addFilterMarker(0);
                    addFilterMarker(1);
                    addedRetire = true;
                    addedMarker = true;
                } else {
                    // 마커가 표시되어 있을 때
                    mapView.removeAllPOIItems();

                    if (!addedRetire) {
                        // 휴게실 마커 추가
                        addFilterMarker(0);
                        addFilterMarker(1);
                        addedAll = false;
                        addedConv = false;
                        addedRest = false;
                        addedCafe = false;
                        addedRetire = true;
                    } else {
                        // 마커 끄기
                        addedRetire = false;
                        addedMarker = false;
                    }
                }
            }
        });

        // 카페 버튼
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addedMarker) {
                    // 마커가 표시되어 있지 않을 때
                    addFilterMarker(2);
                    addedCafe = true;
                    addedMarker = true;
                } else {
                    // 마커가 표시되어 있을 때
                    mapView.removeAllPOIItems();

                    if (!addedCafe) {
                        // 카페 마커 추가
                        addFilterMarker(2);
                        addedAll = false;
                        addedConv = false;
                        addedRest = false;
                        addedCafe = true;
                        addedRetire = false;
                    } else {
                        // 마커 끄기
                        addedCafe = false;
                        addedMarker = false;
                    }
                }
            }
        });

        // 식당 버튼
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addedMarker) {
                    // 마커가 표시되어 있지 않을 때
                    addFilterMarker(3);
                    addedRest = true;
                    addedMarker = true;
                } else {
                    // 마커가 표시되어 있을 때
                    mapView.removeAllPOIItems();

                    if (!addedRest) {
                        // 식당 마커 추가
                        addFilterMarker(3);
                        addedAll = false;
                        addedConv = false;
                        addedRest = true;
                        addedCafe = false;
                        addedRetire = false;
                    } else {
                        // 마커 끄기
                        addedRest = false;
                        addedMarker = false;
                    }
                }
            }
        });

        // 편의점 버튼
        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addedMarker) {
                    // 마커가 표시되어 있지 않을 때
                    addFilterMarker(4);
                    addedConv = true;
                    addedMarker = true;
                } else {
                    // 마커가 표시되어 있을 때
                    mapView.removeAllPOIItems();

                    if (!addedConv) {
                        // 편의점 마커 추가
                        addFilterMarker(4);
                        addedAll = false;
                        addedConv = true;
                        addedRest = false;
                        addedCafe = false;
                        addedRetire = false;
                    } else {
                        // 마커 끄기
                        addedConv = false;
                        addedMarker = false;
                    }
                }
            }
        });

        // 건물 전체 버튼
        fab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!addedMarker) {
                    // 마커가 표시되어 있지 않을 때
                    addAllMarker();
                    addedAll = true;
                    addedMarker = true;
                } else {
                    // 마커가 표시되어 있을 때
                    mapView.removeAllPOIItems();

                    if (!addedAll) {
                        // 전체 건물 마커 추가
                        addAllMarker();
                        addedAll = true;
                        addedConv = false;
                        addedRest = false;
                        addedCafe = false;
                        addedRetire = false;
                    } else {
                        // 마커 끄기
                        addedAll = false;
                        addedMarker = false;
                    }
                }
            }
        });
    }

    private void fabAnimation() {
        if (isFabOpen) {
            fab1.startAnimation(fabClose);
            fab3.startAnimation(fabClose);
            fab4.startAnimation(fabClose);
            fab5.startAnimation(fabClose);
            fab6.startAnimation(fabClose);
            fab1.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);
            fab5.setClickable(false);
            fab6.setClickable(false);
            mapView.removeAllPOIItems();
            addedAll = false;
            addedRetire = false;
            addedCafe = false;
            addedRest = false;
            addedConv = false;
            isFabOpen = false;
        } else {
            fab1.startAnimation(fabOpen);
            fab3.startAnimation(fabOpen);
            fab4.startAnimation(fabOpen);
            fab5.startAnimation(fabOpen);
            fab6.startAnimation(fabOpen);
            fab1.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);
            fab5.setClickable(true);
            fab6.setClickable(true);
            isFabOpen = true;
        }
    }

    private void addAllMarker() {
        NetworkController.getInstance().getApiService().getAllMarkers().enqueue(new Callback<MarkerModel>() {
            @Override
            public void onResponse(@NonNull Call<MarkerModel> call, @NonNull Response<MarkerModel> response) {
                MarkerModel markerModel = response.body();
                ArrayList<Marker> markers = new ArrayList<>();

                ArrayList<BuildingMain> buildingArrayList = markerModel.getBuilding();
                ArrayList<OfficeMain> officeArrayList = markerModel.getOffice();

                for (int i = 0; i < buildingArrayList.size(); i++) {
                    ArrayList<String> officeList = new ArrayList<>();
                    for (int j = 0; j < officeArrayList.size(); j++) {
                        if (buildingArrayList.get(i).getBuildingId().equals(officeArrayList.get(j).getBuildingId())) {
                            officeList.add(officeArrayList.get(j).getTitle());
                        }
                    }
                    Marker marker = new Marker(buildingArrayList.get(i).getBuildingId(),
                            buildingArrayList.get(i).getBuildingName(), buildingArrayList.get(i).getLat(),
                            buildingArrayList.get(i).getLog(), officeList);
                    markers.add(marker);
                }

                for (int i = 0; i < markers.size(); i++) {
                    MapPOIItem marker = new MapPOIItem();
                    // 마커 위치
                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(markers.get(i).getLat(), markers.get(i).getLog()));
                    // 마커 이름
                    marker.setItemName(markers.get(i).getBuildingId() + " " + markers.get(i).getBuildingName());
                    marker.setTag(i);
                    // 커스텀 말풍선 버튼 없애기
                    marker.setShowDisclosureButtonOnCalloutBalloon(false);
                    // 커스텀 마커 모양
                    marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                    // 마커 이미지
                    marker.setCustomImageResourceId(R.drawable.ic_marker);
                    marker.setCustomImageAutoscale(false);
                    mapView.addPOIItem(marker);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MarkerModel> call, @NonNull Throwable t) {

            }
        });
    }

    private void addFilterMarker(final int f_num) {
        NetworkController.getInstance().getApiService().getFilterMarkers(f_num).enqueue(new Callback<ArrayList<FilterModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<FilterModel>> call, @NonNull Response<ArrayList<FilterModel>> response) {
                ArrayList<FilterModel> filter = response.body();

                for (int i = 0; i < filter.size(); i++) {
                    MapPOIItem marker = new MapPOIItem();
                    // 마커 위치
                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(filter.get(i).lat, filter.get(i).log));
                    // 마커 이름
                    marker.setItemName(filter.get(i).buildingId + "호관 " + filter.get(i).title);
                    marker.setTag(f_num);
                    // 마커 모양 커스텀 설정
                    marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                    // 마커 이미지
                    switch (f_num) {
                        case 1:
                            if(filter.get(i).title.substring(0,3).equals("남학생")){
                                marker.setCustomImageResourceId(R.drawable.marker_m_lounge);
                            } else {
                                marker.setCustomImageResourceId(R.drawable.marker_w_lounge);
                            }
                            break;
                        case 2:
                            marker.setCustomImageResourceId(R.drawable.marker_cafe);
                            break;
                        case 3:
                            marker.setCustomImageResourceId(R.drawable.marker_restaurant);
                            break;
                        case 4:
                            marker.setCustomImageResourceId(R.drawable.marker_convenience);
                            break;
                        default:
                            break;
                    }
                    marker.setCustomImageAutoscale(false);
                    mapView.addPOIItem(marker);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<FilterModel>> call, @NonNull Throwable t) {

            }
        });
    }

    // ReverseGeoCodingResultListener 이용시 Override 되는 항목
    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        onFinishReverseGeoCoding("Fail");
    }

    private void onFinishReverseGeoCoding(String result) {
        Toast.makeText(MainActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }

    // CurrentLocationEventListener 이용시 Override 되는 항목
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }


    void checkRunTimePermission() {

        // 위치 정보 허용 체크
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            // 위치 정보가 허용되지 않았을 때, 퍼미션 요청

            // 사용자가 위치 정보를 거부한 경우
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {
                // 위치 정보 허용의 이유 설명 후, 퍼미션 요청
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 정보 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            } else {
                // 사용자가 아직 위치 정보 허용에 대해 설정을 한 적이 없는 경우
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    // ActivityCompat.requestPermissions (permission request result return method)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {
            boolean checkResult = true;

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    checkResult = false;
                    break;
                }
            }

            if (!checkResult) {
                // 퍼미션 거부, 앱 종료
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해주세요. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    // GPS 활성화 메소드
    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 정보를 허용하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GPS_ENABLE_REQUEST_CODE) {
            // GPS 활성화 체크
            if (checkLocationServicesStatus()) {
                if (checkLocationServicesStatus()) {
                    Log.d("@@@", "onActivityResult : GPS 활성화");
                    checkRunTimePermission();
                }
            }
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}