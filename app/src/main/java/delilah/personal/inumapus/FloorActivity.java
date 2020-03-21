package delilah.personal.inumapus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import delilah.personal.inumapus.Adapter.FloorAdapter;
import delilah.personal.inumapus.model.FloorModel;
import delilah.personal.inumapus.network.NetworkController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FloorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloorAdapter adapter;
    private String buildingName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor);

        recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        FloorInformation();

        LinearLayout floorEtc = findViewById(R.id.floorEtc);
        floorEtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EtcActivity.class);

                intent.putExtra("buildingName", buildingName); // buildingName

                startActivity(intent);
            }
        });
    }

    public void FloorInformation() {
        Intent intent = getIntent();
        String number = intent.getExtras().getString("number");

        NetworkController.getInstance().getApiService().getFloor(number).enqueue(new Callback<ArrayList<FloorModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<FloorModel>> call, @NonNull Response<ArrayList<FloorModel>> response) {
                ArrayList<FloorModel> floorModel = response.body();

                String[] floorList = null;
                String buildingId = null;
                int max;
                int index;

                for (int i = 0; i < floorModel.size(); i++) {
                    buildingId = floorModel.get(i).buildingId;
                    buildingName = floorModel.get(i).name;
                    max = floorModel.get(i).max;

                    if (floorModel.get(i).basement == 1) {
                        floorList = new String[max + 1];
                        floorList[0] = "지하 1";
                        index = 1;
                    } else {
                        floorList = new String[max];
                        index = 0;
                    }
                    for (int j = 1; j <= floorModel.get(i).max; j++, index++) {
                        floorList[index] = String.valueOf(j);
                    }
                }
                adapter = new FloorAdapter(FloorActivity.this, floorList, buildingId);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<FloorModel>> call, @NonNull Throwable t) {

            }
        });
    }
}