package com.appcenter.inumapus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.appcenter.inumapus.Adapter.BuildingAdapter;
import com.appcenter.inumapus.model.BuildingModel;
import com.appcenter.inumapus.network.NetworkController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuildingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BuildingAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        GetInformation();
    }

    private void GetInformation() {
        NetworkController.getInstance().getApiService().getBuilding().enqueue(new Callback<ArrayList<BuildingModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<BuildingModel>> call, @NonNull Response<ArrayList<BuildingModel>> response) {
                ArrayList<BuildingModel> building = response.body();

                adapter = new BuildingAdapter(BuildingActivity.this, building);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<BuildingModel>> call, @NonNull Throwable t) {

            }
        });
    }
}