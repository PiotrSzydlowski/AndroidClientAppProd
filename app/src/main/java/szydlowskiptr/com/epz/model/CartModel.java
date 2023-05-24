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
    private List<Item> items;
    private boolean open;
    private boolean tempOpen;
    private double bagCost;

    public CartModel() {
    }

    public CartModel(Long id, Long userId, Long addressId, String reservedStockUntil, boolean reservedStock, Long magId, double itemTotal, double total, double delivery, double totalWeight, boolean emptyBasket, List<Item> items, boolean open, boolean tempOpen, double bagCost) {
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
        this.items = items;
        this.open = open;
        this.tempOpen = tempOpen;
        this.bagCost = bagCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getReservedStockUntil() {
        return reservedStockUntil;
    }

    public void setReservedStockUntil(String reservedStockUntil) {
        this.reservedStockUntil = reservedStockUntil;
    }

    public boolean isReservedStock() {
        return reservedStock;
    }

    public void setReservedStock(boolean reservedStock) {
        this.reservedStock = reservedStock;
    }

    public Long getMagId() {
        return magId;
    }

    public void setMagId(Long magId) {
        this.magId = magId;
    }

    public double getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(double itemTotal) {
        this.itemTotal = itemTotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDelivery() {
        return delivery;
    }

    public void setDelivery(double delivery) {
        this.delivery = delivery;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public boolean isEmptyBasket() {
        return emptyBasket;
    }

    public void setEmptyBasket(boolean emptyBasket) {
        this.emptyBasket = emptyBasket;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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

    public double getBagCost() {
        return bagCost;
    }

    public void setBagCost(double bagCost) {
        this.bagCost = bagCost;
    }
}
