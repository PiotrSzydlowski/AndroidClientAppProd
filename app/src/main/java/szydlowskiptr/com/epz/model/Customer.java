package szydlowskiptr.com.epz.model;

public class Customer {

    private Long id;
    private String email;
    private String phone;
    private String password;

    public Customer(Long id, String email, String phone, String password) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
