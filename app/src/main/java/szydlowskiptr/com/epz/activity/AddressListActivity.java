package szydlowskiptr.com.epz.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.AddressModel;
import szydlowskiptr.com.epz.model.Product;

public class AddressListActivity extends AppCompatActivity {

    RecyclerView addressRecycler;
    AddressAdapter addressAdapter;
    View linerar_for_address;
    ArrayList<AddressModel> addressModelsArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        addressModelsArrayList.add(new AddressModel(1l, "Kzimierzowska 61", true));
        setView();
        setAddressRecycler();
    }

    private void setView() {
        linerar_for_address = findViewById(R.id.linerar_for_address);
        addressRecycler = findViewById(R.id.address_recycler_view);
    }

    private void setAddressRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(linerar_for_address.getContext(), LinearLayoutManager.VERTICAL, false);
        addressRecycler.setLayoutManager(linearLayoutManager);
        addressAdapter = new AddressAdapter(getApplicationContext(), addressModelsArrayList);
        addressRecycler.setAdapter(addressAdapter);
    }
}