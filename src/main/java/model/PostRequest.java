package model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import util.PostComparator;
import util.TagValidation;

import java.util.*;

@Data
public class PostRequest {

    @TagValidation
    private List<String> tags;
    private String sortBy = "ID";
    private String direction = "asc";
    private List<String> validSortOptions;
    private List<String> validDirections;

    public PostRequest() {
        validDirections = new ArrayList<String>() {{
            add("asc");
            add("desc");
        }};
        validSortOptions = new ArrayList<String>() {{
            add("id");
            add("reads");
            add("likes");
            add("popularity");
        }};
    }

    public PostComparator getSortByAsComparator() {
        return PostComparator.valueOf(sortBy);
    }

}
