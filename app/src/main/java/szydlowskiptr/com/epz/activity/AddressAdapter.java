package szydlowskiptr.com.epz.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        holder.addressId.setText(String.valueOf(data.getAddressId()));
        holder.address_text_view.setText(data.getStreet() + "/" + data.getDoorNumber());
        if (!data.getDoorNumber().equals("")) {
            holder.address_text_view.setText(data.getCity() + ", "
                    + data.getStreet() + " " + data.getStreetNumber() + "/" + data.getDoorNumber());
        } else {
            holder.address_text_view.setText(data.getCity() + ", "
                    + data.getStreet() + " " + data.getStreetNumber());
        }
        if (data.isCurrent()) {
            holder.radioButtonAddress.setChecked(true);
        }

        longClickOnSingleAddressListView(holder, data);
        clickOnChangeAddressRadioButton(holder, data);
        clickOnSigleAddressListView(holder);
    }

    private void clickOnSigleAddressListView(@NonNull ViewHolder holder) {
        holder.singleAddressCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO jesli long click zaznaczony i klik to zmieniamy tlo oraz ikone na defoult
            }
        });
    }

    private void clickOnChangeAddressRadioButton(@NonNull ViewHolder holder, AddressModel data) {
        holder.radioButtonAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof AddressListActivity) {
                    ((AddressListActivity) context).callSetCurrentAddress(String.valueOf(data.getAddressId()), String.valueOf(data.getMagId()));
                }
            }
        });
    }

    private void longClickOnSingleAddressListView(@NonNull ViewHolder holder, AddressModel data) {
        holder.singleAddressCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO jesli klik dlugi to zmieniamy tlo dla cardView, ikonę z pinezki na delete, po nacisnieciu delete usuwamy adres - wychodzimy z long clika
                Toast.makeText(context.getApplicationContext(), "Long-tapped on: "+ data.getAddressId(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return newDataArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView address_text_view;
        private RadioButton radioButtonAddress;
        private TextView addressId;
        CardView singleAddressCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            address_text_view = itemView.findViewById(R.id.address_text_view);
            radioButtonAddress = itemView.findViewById(R.id.radioButtonAddress);
            addressId = itemView.findViewById(R.id.addressId);
            singleAddressCardView = itemView.findViewById(R.id.singleAddressCardView);
        }
    }
}
