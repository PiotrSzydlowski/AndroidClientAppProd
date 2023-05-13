package szydlowskiptr.com.epz.model;

/**
 * Created by Piotr Szydlowski on 13.05.2023
 */

public class CreateOrderModel {

    private Long id;
    private double amount;
    private Long customer_id;
    private double delivery_cost;
    private double discount;
    private Long address_id;
    private Long mag_id;
    private int status;
    private int paymentStatus;
    private int slientDelivery;

    public CreateOrderModel(Long id, double amount, Long customer_id, double delivery_cost, double discount, Long address_id, Long mag_id, int status, int paymentStatus, int slientDelivery) {
        this.id = id;
        this.amount = amount;
        this.customer_id = customer_id;
        this.delivery_cost = delivery_cost;
        this.discount = discount;
        this.address_id = address_id;
        this.mag_id = mag_id;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.slientDelivery = slientDelivery;
    }

    public CreateOrderModel() {
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public double getDelivery_cost() {
        return delivery_cost;
    }

    public double getDiscount() {
        return discount;
    }

    public Long getAddress_id() {
        return address_id;
    }

    public Long getMag_id() {
        return mag_id;
    }

    public int getStatus() {
        return status;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public int getSlientDelivery() {
        return slientDelivery;
    }
}
