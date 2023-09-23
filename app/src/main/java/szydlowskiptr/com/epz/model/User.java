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
    private boolean activeOrder;
    private String openFrom;
    private String openTo;
    private boolean open;
    private boolean tempOpen;
    private boolean banned;


    public User(Long id, String login, String role, String magazine, String street,
                String streetNumber, String doorNumber, String postalCode, String city, boolean activeOrder,
                String openFrom, String openTo, boolean open, boolean tempOpen, boolean banned) {
        this.id = id;
        this.login = login;
        this.role = role;
        this.magazine = magazine;
        this.street = street;
        this.streetNumber = streetNumber;
        this.doorNumber = doorNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.activeOrder = activeOrder;
        this.openFrom = openFrom;
        this.openTo = openTo;
        this.tempOpen = tempOpen;
        this.banned = banned;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMagazine() {
        return magazine;
    }

    public void setMagazine(String magazine) {
        this.magazine = magazine;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isActiveOrder() {
        return activeOrder;
    }

    public void setActiveOrder(boolean activeOrder) {
        this.activeOrder = activeOrder;
    }

    public String getOpenFrom() {
        return openFrom;
    }

    public void setOpenFrom(String openFrom) {
        this.openFrom = openFrom;
    }

    public String getOpenTo() {
        return openTo;
    }

    public void setOpenTo(String openTo) {
        this.openTo = openTo;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isTempOpen() {
        return tempOpen;
    }

    public void setTempOpen(boolean tempOpen) {
        this.tempOpen = tempOpen;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }
}
