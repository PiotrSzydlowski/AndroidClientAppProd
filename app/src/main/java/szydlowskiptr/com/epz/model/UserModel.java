package szydlowskiptr.com.epz.model;

import lombok.Data;

/**
 * Created by Piotr Szydlowski on 24.07.2022
 */

@Data
public class UserModel {

    private Long id;
    private String login;
    private String role;
    private String magId;

    public UserModel(Long id, String login, String role, String mag_id) {
        this.id = id;
        this.login = login;
        this.role = role;
        this.magId = mag_id;
    }


    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getRole() {
        return role;
    }

    public String getMagId() {
        return magId;
    }
}
