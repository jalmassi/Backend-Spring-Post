package model;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Ping {
    private HttpStatus status;
    private String statusMessage;
    private boolean success;
}
