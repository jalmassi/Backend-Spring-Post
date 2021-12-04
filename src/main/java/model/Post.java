package model;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Post {
    private Integer id;
    private String author;
    private Integer authorId;
    private Integer likes;
    private Double popularity;
    private Integer reads;
    private String[] tags;
    private HttpStatus status;
    private String statusMessage;
}
