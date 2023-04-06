package szydlowskiptr.com.epz.model;

public class AddressModel {

    private Long id;
    private String address;
    private boolean active;


    public AddressModel(Long id, String address, boolean active) {
        this.id = id;
        this.address = address;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
