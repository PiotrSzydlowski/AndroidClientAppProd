package szydlowskiptr.com.epz.model;

public class CartDao {

    private int id;
    private int qty;

    public CartDao() {
    }

    public CartDao(int id, int qty) {
        this.id = id;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "CartDao{" +
                "id=" + id +
                ", qty=" + qty +
                '}';
    }
}
