package delilah.personal.inumapus;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import delilah.personal.inumapus.Adapter.OfficeAdapter;
import delilah.personal.inumapus.model.OfficeModel;
import delilah.personal.inumapus.network.NetworkController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfficeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OfficeAdapter adapter;
    String buildingId, floorId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        GetInformation();

        EditText editSearch = findViewById(R.id.search);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void GetInformation() {
        Intent intent = getIntent();
        buildingId = Objects.requireNonNull(intent.getExtras()).getString("buildingId");
        if (intent.getExtras().getString("floor").equals("지하 1")) {
            floorId = "0";
        } else {
            floorId = intent.getExtras().getString("floor");
        }

        NetworkController.getInstance().getApiService().getOffice(buildingId, floorId).enqueue(new Callback<ArrayList<OfficeModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<OfficeModel>> call, @NonNull Response<ArrayList<OfficeModel>> response) {
                ArrayList<OfficeModel> officeModels = response.body();

                adapter = new OfficeAdapter(OfficeActivity.this, officeModels);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<OfficeModel>> call, @NonNull Throwable t) {

            }
        });
    }
}