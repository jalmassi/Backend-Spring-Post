package com.justinalmassi.backend.model;

import com.justinalmassi.backend.Validation.DirectionValidation;
import lombok.Data;
import com.justinalmassi.backend.util.PostComparator;
import com.justinalmassi.backend.Validation.TagValidation;

import java.util.*;

@Data
public class PostRequest {

    @TagValidation
    private List<String> tags;
    private String sortBy = "ID";
    @DirectionValidation
    private String direction = "asc";
//    private List<String> validSortOptions;
//    private List<String> validDirections;
//
//    public PostRequest() {
//        validDirections = new ArrayList<String>() {{
//            add("asc");
//            add("desc");
//        }};
//        validSortOptions = new ArrayList<String>() {{
//            add("id");
//            add("reads");
//            add("likes");
//            add("popularity");
//        }};
//    }

    public PostComparator getSortByAsComparator() {
        return PostComparator.valueOf(sortBy);
    }

}
