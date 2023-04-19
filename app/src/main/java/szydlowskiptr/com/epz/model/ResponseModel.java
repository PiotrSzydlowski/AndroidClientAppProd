package szydlowskiptr.com.epz.model;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created by Piotr Szydlowski on 19.04.2023
 */
public class ResponseModel {

    public ResponseModel(String message, Timestamp timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    private String message;
    private Timestamp timestamp;

    public String getMessage() {
        return message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
