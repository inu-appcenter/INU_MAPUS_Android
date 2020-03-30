package com.appcenter.inumapus;

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

import com.appcenter.inumapus.Adapter.PhoneBookAdapter;
import com.appcenter.inumapus.model.PhoneModel;
import com.appcenter.inumapus.network.NetworkController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneBookActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PhoneBookAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook);

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
        NetworkController.getInstance().getApiService().getPhoneInfo().enqueue(new Callback<ArrayList<PhoneModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<PhoneModel>> call, @NonNull Response<ArrayList<PhoneModel>> response) {
                ArrayList<PhoneModel> phone = response.body();

                adapter = new PhoneBookAdapter(PhoneBookActivity.this, phone);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<PhoneModel>> call, @NonNull Throwable t) {

            }
        });
    }
}