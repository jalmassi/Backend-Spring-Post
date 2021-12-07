package com.justinalmassi.backend.service;

import com.justinalmassi.backend.error_handling.PostNotFoundException;
import com.justinalmassi.backend.model.Post;
import com.justinalmassi.backend.model.PostRequest;
import com.justinalmassi.backend.model.PostResponse;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.justinalmassi.backend.util.PostComparator;
import com.justinalmassi.backend.util.ProcessData;


import java.util.*;

@Service
public class PostService {
    RestTemplate restTemplate;
    Post post;
    PostResponse postResponse;
    PostComparator sortBy;
    String direction;

    Map<String, List<Post>> tagsCache = new PassiveExpiringMap<>(600000);

    private void initialize() {
        restTemplate = new RestTemplate();
        postResponse = new PostResponse();
        sortBy = PostComparator.ID;
        direction = "asc";
    }

    public PostResponse getPostService(PostRequest postRequest) {
        initialize();
        postResponse.setPosts(getPost(postRequest));
        ;
        if (postResponse.getPosts().size() == 0) throw new PostNotFoundException();
        ProcessData.removeDuplicatePost(postResponse);
        ProcessData.sortPostResponse(postResponse, sortBy, direction);
        return postResponse;
    }

    public void addTagsToCache(String key, List<Post> value) {
        tagsCache.put(key, value);
    }

    public List<Post> getPost(PostRequest postRequest) {
        List<Post> totalPosts = new ArrayList<>();
        sortBy = postRequest.getSortByAsComparator();
        direction = postRequest.getDirection();

        for (String tag : postRequest.getTags()) {
            if (tagsCache.containsKey(tag)) {
                totalPosts.addAll(tagsCache.get(tag));
            } else {
                totalPosts.addAll(getPostForTag(tag));
            }
        }
        return totalPosts;

    }

    @Async
    private List<Post> getPostForTag(String tag) {
        String url = "https://api.hatchways.io/assessment/blog/posts";
        String fullUrl =
                url + "?tag=" + tag;
        System.out.println("FULLURL: " + fullUrl);
        PostResponse tempPostResponse = restTemplate.getForObject(fullUrl, PostResponse.class);
        tagsCache.put(tag, tempPostResponse.getPosts());
        return tempPostResponse.getPosts();
    }

}
