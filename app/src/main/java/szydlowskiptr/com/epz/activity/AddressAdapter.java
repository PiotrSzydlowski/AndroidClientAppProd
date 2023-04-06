package szydlowskiptr.com.epz.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.AddressModel;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private ArrayList<AddressModel> newDataArray;
    private Context context;


    public AddressAdapter(Context context, ArrayList<AddressModel> newDataArray) {
        this.context = context;
        this.newDataArray = newDataArray;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_address_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {
        AddressModel data = this.newDataArray.get(position);
        holder.address_text_view.setText(data.getAddress());

        if (data.isActive()){
            holder.radioButtonAddress.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return newDataArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView address_text_view;
        private RadioButton radioButtonAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address_text_view = itemView.findViewById(R.id.address_text_view);
            radioButtonAddress = itemView.findViewById(R.id.radioButtonAddress);
        }
    }
}
