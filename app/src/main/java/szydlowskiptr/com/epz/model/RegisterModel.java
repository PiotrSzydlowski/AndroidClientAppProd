package szydlowskiptr.com.epz.model;

import lombok.Data;

/**
 * Created by Piotr Szydlowski on 02.05.2023
 */
@Data
public class RegisterModel {

    private String password;
    private String email;
    private String phone;

    public RegisterModel(String password, String email, String phone) {
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public RegisterModel() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
