package szydlowskiptr.com.epz.activity.repositories;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.activity.AddAddressActivity;
import szydlowskiptr.com.epz.model.AddAddressModel;
import szydlowskiptr.com.epz.model.AddressModel;
import szydlowskiptr.com.epz.service.AddressesService;

public class AddressRepository  {

    List<AddressModel> body = new ArrayList<>();
    Activity activity;

    public AddressRepository(Activity activity) {
        this.activity = activity;
    }

    public void addApiAddress(String userId, AddAddressModel addAddressModel, String tag) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/useraddressess/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AddressesService addressesService = retrofit.create(AddressesService.class);
        Call<List<AddressModel>> call = addressesService.saveAddress(userId, addAddressModel);
        call.enqueue(new Callback<List<AddressModel>>() {

            @Override
            public void onResponse(Call<List<AddressModel>> call, Response<List<AddressModel>> response) {
                body = response.body();
                ((AddAddressActivity) activity).notifyOnResponseFinished();

            }

            @Override
            public void onFailure(Call<List<AddressModel>> call, Throwable t) {

            }
        });
    }

    public List<AddressModel> getAddressList() {
        return body;
    }
}