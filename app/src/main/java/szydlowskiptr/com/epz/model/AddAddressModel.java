package szydlowskiptr.com.epz.model;

/**
 * Created by Piotr Szydlowski on 12.04.2023
 */
public class AddAddressModel {

    private String street;
    private String street_number;
    private String door_number;
    private String flor;
    private String postal_code;
    private String message;
    private String city;

    public AddAddressModel() {
    }

    public AddAddressModel(String street, String street_number, String door_number, String flor, String postal_code, String message, String city) {
        this.street = street;
        this.street_number = street_number;
        this.door_number = door_number;
        this.flor = flor;
        this.postal_code = postal_code;
        this.message = message;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet_number() {
        return street_number;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }

    public String getDoor_number() {
        return door_number;
    }

    public void setDoor_number(String door_number) {
        this.door_number = door_number;
    }

    public String getFlor() {
        return flor;
    }

    public void setFlor(String flor) {
        this.flor = flor;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "AddAddressModel{" +
                "street='" + street + '\'' +
                ", street_number='" + street_number + '\'' +
                ", door_number='" + door_number + '\'' +
                ", flor='" + flor + '\'' +
                ", postal_code='" + postal_code + '\'' +
                ", message='" + message + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
