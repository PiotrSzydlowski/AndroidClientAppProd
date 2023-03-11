package szydlowskiptr.com.epz.model;

import lombok.Data;

/**
 * Created by Piotr Szydlowski on 24.07.2022
 */

@Data
public class User {

    private Long id;
    private String login;
    private String role;

    public User(Long id, String login, String role) {
        this.id = id;
        this.login = login;
        this.role = role;
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
}
