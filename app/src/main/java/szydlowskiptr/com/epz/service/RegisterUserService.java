package szydlowskiptr.com.epz.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import szydlowskiptr.com.epz.model.RegisterModel;
import szydlowskiptr.com.epz.model.User;

/**
 * Created by Piotr Szydlowski on 02.05.2023
 */
public interface RegisterUserService {

    @POST("customers/customerRegister")
    Call<User> registerUser(@Body RegisterModel registerModel);
}
