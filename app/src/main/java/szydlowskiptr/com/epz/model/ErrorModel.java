package szydlowskiptr.com.epz.model;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created by Piotr Szydlowski on 04.05.2023
 */
@Data
public class ErrorModel {

    private String message;
    private String timestamp;

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public ErrorModel(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public ErrorModel() {
    }
}
