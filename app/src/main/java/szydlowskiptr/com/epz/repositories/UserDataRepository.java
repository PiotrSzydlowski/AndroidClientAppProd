package szydlowskiptr.com.epz.repositories;

import android.app.Activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.model.Customer;
import szydlowskiptr.com.epz.profile.ProfileDataActivity;
import szydlowskiptr.com.epz.service.UserService;

public class UserDataRepository {

    public UserDataRepository(Activity activity) {
        this.activity = activity;
    }
    Activity activity;
    Customer body;

    public void updateUserData(Customer customer) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.34:9193/prod/api/customers/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService userService = retrofit.create(UserService.class);
        Call<Customer> call = userService.updateUserData(customer);
        call.enqueue(new Callback<Customer>() {

            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                body = response.body();
                ((ProfileDataActivity) activity).notifyOnResponseFinished();
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });
    }
}
