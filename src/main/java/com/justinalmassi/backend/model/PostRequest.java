package com.justinalmassi.backend.model;

import com.justinalmassi.backend.Validation.DirectionValidation;
import com.justinalmassi.backend.error_handling.SortByException;
import lombok.Data;
import com.justinalmassi.backend.util.PostComparator;
import com.justinalmassi.backend.Validation.TagValidation;

import java.util.*;

@Data
public class PostRequest {

    @TagValidation
    private List<String> tags;
    private String sortBy;
    @DirectionValidation
    private String direction;
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
        PostComparator sortByComparator;
        try{
        sortByComparator = PostComparator.valueOf(sortBy);}
        catch(Exception e){
            throw new SortByException();
        }
        return sortByComparator;
    }

}
