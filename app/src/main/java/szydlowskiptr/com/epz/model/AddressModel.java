package szydlowskiptr.com.epz.model;

public class AddressModel {

    private Long userAddressId;
    private Long addressId;
    private String street;
    private String streetNumber;
    private String doorNumber;
    private String postalCode;
    private String message;
    private String city;
    private int magId;
    private boolean current;

    public AddressModel(Long userAddressId, Long addressId, String street, String streetNumber,
                        String doorNumber, String postalCode, String message, String city,
                        int magId, boolean current) {
        this.userAddressId = userAddressId;
        this.addressId = addressId;
        this.street = street;
        this.streetNumber = streetNumber;
        this.doorNumber = doorNumber;
        this.postalCode = postalCode;
        this.message = message;
        this.city = city;
        this.magId = magId;
        this.current = current;
    }

    public Long getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Long userAddressId) {
        this.userAddressId = userAddressId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public int getMagId() {
        return magId;
    }

    public void setMagId(int magId) {
        this.magId = magId;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "AddressModel{" +
                "userAddressId=" + userAddressId +
                ", addressId=" + addressId +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", doorNumber='" + doorNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", message='" + message + '\'' +
                ", city='" + city + '\'' +
                ", magId=" + magId +
                ", current=" + current +
                '}';
    }
}
