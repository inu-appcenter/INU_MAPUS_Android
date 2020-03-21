package delilah.personal.inumapus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import delilah.personal.inumapus.R;
import delilah.personal.inumapus.model.EmployeeModel;

public class EmployeeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<EmployeeModel> items;
    private String email, phone;

    public EmployeeAdapter(Context context, ArrayList<EmployeeModel> items) {
        this.context = context;
        this.items = items;
    }

    private static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView detailOrgan, position, name, phoneNumber, email;

        private EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);

            detailOrgan = itemView.findViewById(R.id.organ);
            position = itemView.findViewById(R.id.position);
            name = itemView.findViewById(R.id.name);
            phoneNumber = itemView.findViewById(R.id.number);
            email = itemView.findViewById(R.id.email);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_employee, parent, false);

        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final EmployeeViewHolder viewHolder = (EmployeeViewHolder) holder;
        final EmployeeModel item = items.get(position);
        if(item.getTelephone().length()>0){
            phone = "l " + item.getTelephone();
        }
        if(item.getEmail().length()>0){
            email = item.getEmail();
        }

        viewHolder.detailOrgan.setText(item.getDetailOrgan());
        viewHolder.position.setText(item.getPosition());
        viewHolder.name.setText(item.getName());
        viewHolder.phoneNumber.setText(phone);
        viewHolder.email.setText(email);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}