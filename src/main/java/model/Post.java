package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    private Integer id;
    private String author;
    private Integer authorId;
    private Integer likes;
    private Double popularity;
    private Integer reads;
    private String[] tags;
}
