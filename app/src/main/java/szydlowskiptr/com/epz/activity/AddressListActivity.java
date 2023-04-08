package szydlowskiptr.com.epz.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.AddressModel;
import szydlowskiptr.com.epz.service.AddressesService;

public class AddressListActivity extends AppCompatActivity {

    RecyclerView addressRecycler;
    AddressAdapter addressAdapter;
    View linerar_for_address;
    ArrayList<AddressModel> addressModelsArrayList = new ArrayList<>();
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        sp = getApplication().getSharedPreferences("preferences", MODE_PRIVATE);
        setView();
        callApiGetAddressesByUser();
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

    private void callApiGetAddressesByUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/useraddressess/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AddressesService addressesService = retrofit.create(AddressesService.class);
        Call<List<AddressModel>> call = addressesService.getAddresses(sp.getString("user_id", null));
        call.enqueue(new Callback<List<AddressModel>>() {
            @Override
            public void onResponse(Call<List<AddressModel>> call, Response<List<AddressModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AddressModel> body = response.body();
                    addressModelsArrayList.addAll(body);
                    parseArrayAddresses();
                }
            }
            @Override
            public void onFailure(Call<List<AddressModel>> call, Throwable t) {

            }
        });
    }

    private void parseArrayAddresses() {
        try {
            addressAdapter = new AddressAdapter(getApplication(), addressModelsArrayList);
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        setAddressRecycler();
    }
}