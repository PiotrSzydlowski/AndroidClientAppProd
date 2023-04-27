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
    private String image;
    private String name;
    private String description;
    private double priceBeforePromo;
    private int quantityOnStock;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPriceBeforePromo() {
        return priceBeforePromo;
    }

    public void setPriceBeforePromo(double priceBeforePromo) {
        this.priceBeforePromo = priceBeforePromo;
    }

    public int getQuantityOnStock() {
        return quantityOnStock;
    }

    public void setQuantityOnStock(int quantityOnStock) {
        this.quantityOnStock = quantityOnStock;
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
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priceBeforePromo=" + priceBeforePromo +
                ", quantityOnStock=" + quantityOnStock +
                '}';
    }
}
