package szydlowskiptr.com.epz.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import szydlowskiptr.com.epz.model.AddAddressModel;
import szydlowskiptr.com.epz.model.AddressModel;

/**
 * Created by Piotr Szydlowski on 07.04.2023
 */
public interface AddressesService {

    @GET("userAddresses/{userId}")
    Call<List<AddressModel>> getAddresses(@Path("userId") String id);

    @POST("setCurrentaddress/{addressId}/{userId}")
    Call<List<AddressModel>> setCurrentAddress(@Path("addressId") String addressId, @Path("userId") String userId);

    @POST("addAddressByUser/{userId}")
    Call<List<AddressModel>> saveAddress(@Path("userId") String userId, @Body AddAddressModel addAddressModelBody);

    @DELETE("deleteUserAddress/{addressId}/userId/{userId}")
    Call<List<AddressModel>> deleteAddress(@Path("addressId") String addressId, @Path("userId") String userId);
}
