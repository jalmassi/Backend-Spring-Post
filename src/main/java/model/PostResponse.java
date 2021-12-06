package model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class PostResponse {
    private List<Post> posts;
    HttpStatus status;
    String statusMessage;
}
