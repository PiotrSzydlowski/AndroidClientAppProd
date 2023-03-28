package szydlowskiptr.com.epz.model;

public class Product {

    private Long id;
    private String ean;
    private String name;
    private String description;
    private Long categoryId;
    private String categoryName;
    private boolean categoryActive;
    private Long subcategoryId;
    private String subcategoryName;
    private Long subcategoryParentId;
    private String unitName;
    private int photo;
    private boolean active;
    private int weight;

    private int qty;

    public Product(Long id, String ean, String name, String description, Long categoryId,
                   String categoryName, boolean categoryActive, Long subcategoryId,
                   String subcategoryName, Long subcategoryParentId, String unitName, int photo,
                   boolean active, int weight) {
        this.id = id;
        this.ean = ean;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryActive = categoryActive;
        this.subcategoryId = subcategoryId;
        this.subcategoryName = subcategoryName;
        this.subcategoryParentId = subcategoryParentId;
        this.unitName = unitName;
        this.photo = photo;
        this.active = active;
        this.weight = weight;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isCategoryActive() {
        return categoryActive;
    }

    public void setCategoryActive(boolean categoryActive) {
        this.categoryActive = categoryActive;
    }

    public Long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public Long getSubcategoryParentId() {
        return subcategoryParentId;
    }

    public void setSubcategoryParentId(Long subcategoryParentId) {
        this.subcategoryParentId = subcategoryParentId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", ean='" + ean + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryActive=" + categoryActive +
                ", subcategoryId=" + subcategoryId +
                ", subcategoryName='" + subcategoryName + '\'' +
                ", subcategoryParentId=" + subcategoryParentId +
                ", unitName='" + unitName + '\'' +
                ", photo=" + photo +
                ", active=" + active +
                ", weight=" + weight +
                ", qty=" + qty +
                '}';
    }
}



