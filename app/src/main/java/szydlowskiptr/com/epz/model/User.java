package szydlowskiptr.com.epz.model;

import lombok.Data;

/**
 * Created by Piotr Szydlowski on 24.07.2022
 */

@Data
public class User {

    private Long id;
    private String login;
    private String role;
    private String magazine;
    private String street;
    private String streetNumber;
    private String doorNumber;
    private String postalCode;
    private String city;


    public User(Long id, String login, String role, String magazine, String street, String streetNumber, String doorNumber, String postalCode, String city) {
        this.id = id;
        this.login = login;
        this.role = role;
        this.magazine = magazine;
        this.street = street;
        this.streetNumber = streetNumber;
        this.doorNumber = doorNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getRole() {
        return role;
    }

    public String getMagazine() {
        return magazine;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", role='" + role + '\'' +
                ", magazine='" + magazine + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", doorNumber='" + doorNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
