package szydlowskiptr.com.epz.model;

public class OrderStatus {

    private String postalCode;
    private String city;
    private String street;
    private String streetNumber;
    private String doorNumber;
    private String floor;
    private String message;
    private String latitude;
    private String longtitdute;
    private int orderStatusInfo;
    private Long pickerId;
    private Long getDeliverCourierId;
    private int slientDelivery;

    public OrderStatus() {
    }

    public OrderStatus(String postalCode, String city, String street, String streetNumber,
                       String doorNumber, String floor, String message, String latitude,
                       String longtitdute, int orderStatusInfo, Long pickerId, Long getDeliverCourierId,
                       int slientDelivery) {
        this.postalCode = postalCode;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.doorNumber = doorNumber;
        this.floor = floor;
        this.message = message;
        this.latitude = latitude;
        this.longtitdute = longtitdute;
        this.orderStatusInfo = orderStatusInfo;
        this.pickerId = pickerId;
        this.getDeliverCourierId = getDeliverCourierId;
        this.slientDelivery = slientDelivery;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
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

    public String getFloor() {
        return floor;
    }

    public String getMessage() {
        return message;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongtitdute() {
        return longtitdute;
    }

    public int getOrderStatusInfo() {
        return orderStatusInfo;
    }

    public Long getPickerId() {
        return pickerId;
    }

    public Long getGetDeliverCourierId() {
        return getDeliverCourierId;
    }

    public int getSlientDelivery() {
        return slientDelivery;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", doorNumber='" + doorNumber + '\'' +
                ", floor='" + floor + '\'' +
                ", message='" + message + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longtitdute='" + longtitdute + '\'' +
                ", orderStatus=" + orderStatusInfo +
                ", pickerId=" + pickerId +
                ", getDeliverCourierId=" + getDeliverCourierId +
                ", slientDelivery=" + slientDelivery +
                '}';
    }
}
