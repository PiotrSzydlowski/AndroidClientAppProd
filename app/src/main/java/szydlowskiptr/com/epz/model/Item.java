package szydlowskiptr.com.epz.model;

import lombok.Data;

public class Item {

    private Long basketId;
    private Long stockItemId;
    private int productQuantityInBasket;
    private Double price;
    private int reservedQuantity;
    private Long productId;
    private double weight;

    public Item(Long basketId, Long stockItemId, int productQuantityInBasket, Double price, int reservedQuantity, Long productId, double weight) {
        this.basketId = basketId;
        this.stockItemId = stockItemId;
        this.productQuantityInBasket = productQuantityInBasket;
        this.price = price;
        this.reservedQuantity = reservedQuantity;
        this.productId = productId;
        this.weight = weight;
    }

    public Item() {
    }

    public Long getBasketId() {
        return basketId;
    }

    public void setBasketId(Long basketId) {
        this.basketId = basketId;
    }

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemId) {
        this.stockItemId = stockItemId;
    }

    public int getProductQuantityInBasket() {
        return productQuantityInBasket;
    }

    public void setProductQuantityInBasket(int productQuantityInBasket) {
        this.productQuantityInBasket = productQuantityInBasket;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(int reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Item{" +
                "basketId=" + basketId +
                ", stockItemId=" + stockItemId +
                ", productQuantityInBasket=" + productQuantityInBasket +
                ", price=" + price +
                ", reservedQuantity=" + reservedQuantity +
                ", productId=" + productId +
                ", weight=" + weight +
                '}';
    }
}
