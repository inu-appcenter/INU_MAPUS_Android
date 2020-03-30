package com.appcenter.inumapus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.appcenter.inumapus.FloorActivity;
import com.appcenter.inumapus.R;
import com.appcenter.inumapus.model.BuildingModel;

public class BuildingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<BuildingModel> items;

    public BuildingAdapter(Context context, ArrayList<BuildingModel> items) {
        this.context = context;
        this.items = items;
    }

    private static class BuildingViewHolder extends RecyclerView.ViewHolder {
        TextView buildingTitle, buildingNumber;

        private BuildingViewHolder(@NonNull View itemView) {
            super(itemView);

            buildingTitle = itemView.findViewById(R.id.title);
            buildingNumber = itemView.findViewById(R.id.number);
        }
    }

    @NonNull
    @Override
    public BuildingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_building, viewGroup, false);

        return new BuildingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final BuildingViewHolder viewHolder = (BuildingViewHolder) holder;
        final BuildingModel item = items.get(position);
        String buildingNumber = item.getNumber() + "호관";

        viewHolder.buildingTitle.setText(item.getName());
        viewHolder.buildingNumber.setText(buildingNumber);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FloorActivity.class);

                intent.putExtra("number", item.getNumber());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}