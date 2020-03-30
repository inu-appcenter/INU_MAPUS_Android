package com.appcenter.inumapus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.appcenter.inumapus.EmployeeActivity;
import com.appcenter.inumapus.R;
import com.appcenter.inumapus.model.OfficeModel;

public class OfficeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<OfficeModel> unfilteredList, filteredList;

    public OfficeAdapter(Context context, ArrayList<OfficeModel> items) {
        this.context = context;
        this.unfilteredList = items;
        this.filteredList = items;
    }

    private static class OfficeViewHolder extends RecyclerView.ViewHolder {
        TextView roomId, officeTitle;

        private OfficeViewHolder(@NonNull View itemView) {
            super(itemView);

            roomId = itemView.findViewById(R.id.number);
            officeTitle = itemView.findViewById(R.id.title);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_office, viewGroup, false);

        return new OfficeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final OfficeViewHolder viewHolder = (OfficeViewHolder) holder;
        final OfficeModel item = filteredList.get(position);

        viewHolder.roomId.setText(item.getRoomId());
        viewHolder.officeTitle.setText(item.getTitle());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EmployeeActivity.class);

                intent.putExtra("officeId", item.getOfficeId());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charText = charSequence.toString();
                if(charText.isEmpty()) {
                    filteredList = unfilteredList;
                } else {
                    ArrayList<OfficeModel> filteringList = new ArrayList<>();
                    for(OfficeModel office : unfilteredList) {
                        String name = office.getTitle();
                        String number = office.getRoomId();
                        if (name.toLowerCase().contains(charText)||number.toLowerCase().contains(charText)) {
                            filteringList.add(office);
                        }
                    }
                    filteredList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }
}