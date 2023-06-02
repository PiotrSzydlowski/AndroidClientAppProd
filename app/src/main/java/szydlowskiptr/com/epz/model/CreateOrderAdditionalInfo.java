package szydlowskiptr.com.epz.model;

public class CreateOrderAdditionalInfo {

    private String message;
    private int slientDelivery;

    public CreateOrderAdditionalInfo() {
    }

    public CreateOrderAdditionalInfo(String message, int slientDelivery) {
        this.message = message;
        this.slientDelivery = slientDelivery;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSlientDelivery(int slientDelivery) {
        this.slientDelivery = slientDelivery;
    }
}
