package delilah.personal.inumapus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import delilah.personal.inumapus.Adapter.EmployeeAdapter;
import delilah.personal.inumapus.model.EmployeeModel;
import delilah.personal.inumapus.network.NetworkController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeActivity extends AppCompatActivity {
    private TextView noEmployee;
    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private int viewChanger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        noEmployee = findViewById(R.id.noEmployee);
        recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        GetInformation();
    }

    private void GetInformation() {
        Intent intent = getIntent();
        int officeId = intent.getExtras().getInt("officeId");
        NetworkController.getInstance().getApiService().getEmployee(officeId).enqueue(new Callback<ArrayList<EmployeeModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<EmployeeModel>> call, @NonNull Response<ArrayList<EmployeeModel>> response) {
                ArrayList<EmployeeModel> employeeModels = response.body();

                viewChanger = employeeModels.size();

                if(viewChanger==0){
                    recyclerView.setVisibility(View.GONE);
                    noEmployee.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noEmployee.setVisibility(View.GONE);
                }

                adapter = new EmployeeAdapter(EmployeeActivity.this, employeeModels);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<EmployeeModel>> call, @NonNull Throwable t) {

            }
        });
    }
}