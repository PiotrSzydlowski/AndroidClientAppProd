package szydlowskiptr.com.epz.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import szydlowskiptr.com.epz.model.AddressModel;
import szydlowskiptr.com.epz.model.Customer;

public interface UserService {

    @PUT("updateCustomer")
    Call<Customer> updateUserData(@Body Customer customer);

    @DELETE("deleteAcoount/{userId}")
    Call<Void> deleteCustomer(@Path("userId") String userId);
}
