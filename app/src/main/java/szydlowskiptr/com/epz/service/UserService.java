package szydlowskiptr.com.epz.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import szydlowskiptr.com.epz.model.Customer;

public interface UserService {

    @PUT("updateCustomer")
    Call<Customer> updateUserData(@Body Customer customer);
}
