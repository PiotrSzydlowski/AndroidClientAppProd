package szydlowskiptr.com.epz.service;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import szydlowskiptr.com.epz.model.User;
import szydlowskiptr.com.epz.model.UserLog;

/**
 * Created by Piotr Szydlowski on 24.07.2022
 */
public interface LogUser {

    @Headers({"login-type: email"})
    @POST("user/login")
    Call<User> logInUser(@Body UserLog userLog);
}
