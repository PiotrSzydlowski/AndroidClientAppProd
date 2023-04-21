package szydlowskiptr.com.epz.interfacesCaller;

/**
 * Created by Piotr Szydlowski on 11.04.2023
 */
public interface AddressCaller {

    void callSetCurrentAddress(String addressId, String magId);

    void callDeleteUserAddress(String addressId);
}
