package szydlowskiptr.com.epz.model;

public class ProductModel {

    private Long id;
    private String productsName;
    private String productDescription;
    private Long productId;
    private Long quantity;
    private double price;
    private boolean is_new;
    private boolean hit;
    private boolean promo;
    private boolean cold;
    private boolean active;
    private int reservedQuantity;
    private double priceBeforePromo;
    private String photo;

    public ProductModel(Long id, String productsName, String productDescription, Long productId,
                        Long quantity, double price, boolean is_new, boolean hit, boolean promo,
                        boolean cold, boolean active, int reservedQuantity, double priceBeforePromo, String photo) {
        this.id = id;
        this.productsName = productsName;
        this.productDescription = productDescription;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.is_new = is_new;
        this.hit = hit;
        this.promo = promo;
        this.cold = cold;
        this.active = active;
        this.reservedQuantity = reservedQuantity;
        this.priceBeforePromo = priceBeforePromo;
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public String getProductsName() {
        return productsName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public boolean isIs_new() {
        return is_new;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isPromo() {
        return promo;
    }

    public boolean isCold() {
        return cold;
    }

    public boolean isActive() {
        return active;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public double getPriceBeforePromo() {
        return priceBeforePromo;
    }

    public String getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productsName='" + productsName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", is_new=" + is_new +
                ", hit=" + hit +
                ", promo=" + promo +
                ", cold=" + cold +
                ", active=" + active +
                ", reservedQuantity=" + reservedQuantity +
                ", priceBeforePromo=" + priceBeforePromo +
                ", photo='" + photo + '\'' +
                '}';
    }
}



