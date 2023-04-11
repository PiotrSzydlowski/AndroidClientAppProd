package szydlowskiptr.com.epz.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import szydlowskiptr.com.epz.model.AddressModel;
import szydlowskiptr.com.epz.model.Category;

/**
 * Created by Piotr Szydlowski on 07.04.2023
 */
public interface AddressesService {

    @GET("userAddresses/{userId}")
    Call<List<AddressModel>> getAddresses(@Path("userId") String id);

    @POST("setCurrentaddress/{addressId}/{userId}")
    Call<List<AddressModel>> setCurrentAddress(@Path("addressId") String addressId, @Path("userId") String userId);
}
