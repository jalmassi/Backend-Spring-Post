package com.justinalmassi.backend.util;

import com.justinalmassi.backend.model.Post;
import com.justinalmassi.backend.model.PostResponse;

import java.util.*;

public class ProcessData {

    public static PostResponse removeDuplicatePost(PostResponse postResponse) {
        if (postResponse.getPosts().size() < 1) return postResponse;
        Set<Post> setOfPosts = new HashSet<>(postResponse.getPosts());
        postResponse.setPosts(new ArrayList<Post>(setOfPosts));
        return postResponse;
    }


    public static PostResponse sortPostResponse(PostResponse postResponse, PostComparator sortBy, String direction) {
        List<Post> posts = postResponse.getPosts();
        Comparator<Post> comparatorInstance = direction.equals("desc") ? PostComparator.descending(PostComparator.getComparator(sortBy)) : PostComparator.getComparator(sortBy);
        Collections.sort(posts, comparatorInstance);
        postResponse.setPosts(posts);
        return postResponse;
    }

}
