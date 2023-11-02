package szydlowskiptr.com.epz.model;

import java.util.List;

public class CartModel {

    private Long id;
    private Long userId;
    private Long addressId;
    private String reservedStockUntil;
    private boolean reservedStock;
    private Long magId;
    private double itemTotal;
    private double total;
    private double delivery;
    private double totalWeight;
    private boolean emptyBasket;
    private boolean activeOrder;
    private String message;
    private List<Item> items;
    private boolean open;
    private boolean tempOpen;
    private double bagCost;
    private boolean userBanned;
    private OrderStatus orderStatus;

    public CartModel() {
    }

    public CartModel(Long id, Long userId, Long addressId, String reservedStockUntil,
                     boolean reservedStock, Long magId, double itemTotal, double total,
                     double delivery, double totalWeight, boolean emptyBasket, boolean activeOrder,
                     String message, List<Item> items, boolean open, boolean tempOpen, double bagCost,
                     boolean userBanned, OrderStatus orderStatus) {
        this.id = id;
        this.userId = userId;
        this.addressId = addressId;
        this.reservedStockUntil = reservedStockUntil;
        this.reservedStock = reservedStock;
        this.magId = magId;
        this.itemTotal = itemTotal;
        this.total = total;
        this.delivery = delivery;
        this.totalWeight = totalWeight;
        this.emptyBasket = emptyBasket;
        this.activeOrder = activeOrder;
        this.message = message;
        this.items = items;
        this.open = open;
        this.tempOpen = tempOpen;
        this.bagCost = bagCost;
        this.userBanned = userBanned;
        this.orderStatus = orderStatus;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public String getReservedStockUntil() {
        return reservedStockUntil;
    }

    public boolean isReservedStock() {
        return reservedStock;
    }

    public Long getMagId() {
        return magId;
    }

    public double getItemTotal() {
        return itemTotal;
    }

    public double getTotal() {
        return total;
    }

    public double getDelivery() {
        return delivery;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public boolean isEmptyBasket() {
        return emptyBasket;
    }

    public boolean isActiveOrder() {
        return activeOrder;
    }

    public String getMessage() {
        return message;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isTempOpen() {
        return tempOpen;
    }

    public double getBagCost() {
        return bagCost;
    }

    public boolean isUserBanned() {
        return userBanned;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    @Override
    public String toString() {
        return "CartModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", addressId=" + addressId +
                ", reservedStockUntil='" + reservedStockUntil + '\'' +
                ", reservedStock=" + reservedStock +
                ", magId=" + magId +
                ", itemTotal=" + itemTotal +
                ", total=" + total +
                ", delivery=" + delivery +
                ", totalWeight=" + totalWeight +
                ", emptyBasket=" + emptyBasket +
                ", activeOrder=" + activeOrder +
                ", message='" + message + '\'' +
                ", items=" + items +
                ", open=" + open +
                ", tempOpen=" + tempOpen +
                ", bagCost=" + bagCost +
                ", userBanned=" + userBanned +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
