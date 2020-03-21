package delilah.personal.inumapus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import delilah.personal.inumapus.R;
import delilah.personal.inumapus.model.PhoneModel;

public class PhoneBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<PhoneModel> unfilteredList, filteredList;

    public PhoneBookAdapter(Context context, ArrayList<PhoneModel> items) {
        this.context = context;
        this.unfilteredList = items;
        this.filteredList = items;
    }

    private static class PhoneBookViewHolder extends RecyclerView.ViewHolder {
        TextView detailOrgan, position, name, phoneNumber;
        ImageButton btnCall;

        private PhoneBookViewHolder(@NonNull View itemView) {
            super(itemView);

            detailOrgan = itemView.findViewById(R.id.organ);
            position = itemView.findViewById(R.id.position);
            name = itemView.findViewById(R.id.name);
            phoneNumber = itemView.findViewById(R.id.number);

            btnCall = itemView.findViewById(R.id.buttonCall);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_phonebook, viewGroup, false);

        return new PhoneBookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final PhoneBookViewHolder viewHolder = (PhoneBookViewHolder) holder;
        final PhoneModel item = filteredList.get(position);

        viewHolder.detailOrgan.setText(item.getDetailOrgan());
        viewHolder.position.setText(item.getPosition());
        viewHolder.name.setText(item.getName());
        viewHolder.phoneNumber.setText(item.getTelephone());

        viewHolder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, item.getName() + "의 전화번호입니다.", Toast.LENGTH_SHORT).show();

                String phone = "tel:" + item.getTelephone();
                Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse(phone));

                context.startActivity(call);
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
                    ArrayList<PhoneModel> filteringList = new ArrayList<>();
                    for(PhoneModel phone : unfilteredList) {
                        String name = phone.getName();
                        String number = phone.getTelephone();
                        if (name.toLowerCase().contains(charText)||number.toLowerCase().contains(charText)) {
                            filteringList.add(phone);
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