package szydlowskiptr.com.epz.address;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rollbar.android.Rollbar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.databinding.ActivityAddressListBinding;
import szydlowskiptr.com.epz.interfacesCaller.AddressCaller;
import szydlowskiptr.com.epz.model.AddressModel;
import szydlowskiptr.com.epz.service.AddressesService;

public class AddressListActivity extends AppCompatActivity implements AddressCaller {

    ActivityAddressListBinding binding;
    RecyclerView addressRecycler;
    AddressAdapter addressAdapter;
    ArrayList<AddressModel> addressModelsArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addressModelsArrayList.removeAll(addressModelsArrayList);
        clickOnAddAddress();
        callApiGetAddressesByUser();
        setAddressRecycler();
        Rollbar.init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callApiGetAddressesByUser();
    }

    private void setAddressRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.linerarForAddress.getContext(), LinearLayoutManager.VERTICAL, false);
        binding.addressRecyclerView.setLayoutManager(linearLayoutManager);
        addressAdapter = new AddressAdapter(getApplicationContext(), addressModelsArrayList);
        addressRecycler.setAdapter(addressAdapter);
    }

    private void clickOnAddAddress(){
        binding.cardViewAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddAddressActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_letf);
            }
        });
    }

    private void callApiGetAddressesByUser() {
        Retrofit retrofit = getRetrofit();
        AddressesService addressesService = retrofit.create(AddressesService.class);
        Call<List<AddressModel>> call = addressesService.getAddresses(PrefConfig.loadUserIdFromPref(getApplicationContext()));
        call.enqueue(new Callback<List<AddressModel>>() {
            @Override
            public void onResponse(Call<List<AddressModel>> call, Response<List<AddressModel>> response) {
                addressModelsArrayList.removeAll(addressModelsArrayList);
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


    @Override
    public void callSetCurrentAddress(String addressId, String magId) {
        callApiSetCurrentAddress(addressId, magId);
    }

    @Override
    public void callDeleteUserAddress(String addressId) {
      callDeleteAddress(addressId);
    }

    private void callDeleteAddress(String addressId) {
        Retrofit retrofit = getRetrofit();
        AddressesService addressesService = retrofit.create(AddressesService.class);
        Call<List<AddressModel>> call = addressesService.deleteAddress(addressId,PrefConfig.loadUserIdFromPref(getApplicationContext()));
        call.enqueue(new Callback<List<AddressModel>>() {
            @Override
            public void onResponse(Call<List<AddressModel>> call, Response<List<AddressModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    addressModelsArrayList.removeAll(addressModelsArrayList);
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

    private void callApiSetCurrentAddress(String addressId, String magId) {
        Retrofit retrofit = getRetrofit();
        AddressesService addressesService = retrofit.create(AddressesService.class);
        Call<List<AddressModel>> call = addressesService.setCurrentAddress(addressId,PrefConfig.loadUserIdFromPref(getApplicationContext()));
        call.enqueue(new Callback<List<AddressModel>>() {
            @Override
            public void onResponse(Call<List<AddressModel>> call, Response<List<AddressModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    addressModelsArrayList.removeAll(addressModelsArrayList);
                    List<AddressModel> body = response.body();
                    addressModelsArrayList.addAll(body);
                    parseArrayAddresses();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    List<AddressModel> collect = addressModelsArrayList.stream()
                            .filter(x -> x.isCurrent())
                            .collect(Collectors.toList());
                    PrefConfig.saveMagIdInPref(getApplicationContext(),String.valueOf(collect.get(0).getMagId()));
                    PrefConfig.saveAddressDoorNumberInPref(getApplicationContext(), collect.get(0).getDoorNumber());
                    PrefConfig.saveAddressStreetInPref(getApplicationContext(), collect.get(0).getStreet());
                    PrefConfig.saveAddressStreetNumberInPref(getApplicationContext(), collect.get(0).getStreetNumber());
                    PrefConfig.saveCityInPref(getApplicationContext(), collect.get(0).getCity());
                    PrefConfig.savePostalCodeInPref(getApplicationContext(),  collect.get(0).getPostalCode());
                }
            }
            @Override
            public void onFailure(Call<List<AddressModel>> call, Throwable t) {

            }
        });
    }

    @NonNull
    private Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.34:9193/prod/api/useraddressess/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}