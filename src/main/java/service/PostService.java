package service;

import model.Post;
import model.PostRequest;
import model.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import util.GlobalVariables;
import util.PostComparator;
import util.ProcessData;


import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class PostService {
    RestTemplate restTemplate;
    Post post;
    PostResponse postResponse;
    PostComparator sortBy;
    String direction;
    private String url;
//        @Autowired
//    private GlobalVariables uri;
    Map<String, List<Post>> tagsCache = new PassiveExpiringMap<>(600000);

    @Value("${api.url}")
    public void setMyUrl(String url) {
        this.url = url;}

    private void initialize() {
        restTemplate = new RestTemplate();
        postResponse = new PostResponse();
        sortBy = PostComparator.ID;
        direction = "asc";
    }

    public PostResponse getPostServiceForRequest(PostRequest postRequest) {
        initialize();
        postResponse.setPosts(getPost(postRequest));
        ;
        if (postResponse.getPosts().size() == 0) return postResponse;
        ProcessData.removeDuplicatePost(postResponse);
        getUniquePostFromPostResponse();
        ProcessData.sortPostResponse(postResponse, sortBy, direction);
        return postResponse;
    }

    public void addTagsToCache(String key, List<Post> value) {
        tagsCache.put(key, value);
    }

    /**
     * It validates given post request have valid paramters or not.
     * For tags it checks if  given tag is valid string
     * For sortby it checks if its value is from acceptable sortby list.
     * For direction it checks if its value is from acceptable direction list.
     *
     * @param postRequest consist of tags, sortby and direction parameters.
     * @return Post with status message and code for invalid parameters.
     */
//    public Post validateRequestParameter(PostRequest postRequest) {
//           PostResponse post = new PostResponse();
//           List<String> validTags = validateTags(postRequest.getTags());
//
//            if(postRequest.getTags() == null || postRequest.getTags().length == 0 || validTags == null || validTags.size() == 0) {
//                post.setStatus(HttpStatus.BAD_REQUEST);
//                post.setStatusMessage("Tags parameter is required");
//                return post;
//            }
//
//           if(!postRequest.getValidDirections().contains(postRequest.getDirection())){
//               post.setStatus(HttpStatus.BAD_REQUEST);
//               post.setStatusMessage("Direction parameter is invalid");
//               return post;
//           }
//
//           if(!postRequest.getValidDirections().contains(postRequest.getSortBy())){
//               post.setStatus(HttpStatus.BAD_REQUEST);
//               post.setStatusMessage("Sort parameter is invalid");
//               return post;
//           }
//
//           return post;
//    }

//    private List<String> validateTags(String[] tagsArray){
//        if(tagsArray == null || tagsArray.length == 0) return null;
//
//        List<String> outputTags = new ArrayList<>();
//        for(String  str : tagsArray){
//            if(isValidString(str)){
//                outputTags.add(str);
//            }
//        }
//        return outputTags;
//    }
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
        String fullUrl =
                this.url + "?tag=" + tag;
        System.out.println("FULLURL: " + fullUrl);
        PostResponse tempPostResponse = restTemplate.getForObject(fullUrl, PostResponse.class);
        tagsCache.put(tag, tempPostResponse.getPosts());
        return tempPostResponse.getPosts();
    }


    /**
     * Convert Post set into uniquePostResponse list.
     */
    private void getUniquePostFromPostResponse() {
        Iterator postStream = postResponse.getPosts().iterator();
        List<Post> uniquePostList = new ArrayList<>();
        while (postStream.hasNext()) {
            uniquePostList.add((Post) postStream.next());
        }
        postResponse.setPosts(uniquePostList);
    }

    /**
     * Sort unique post list according to sort by parameter
     * Place them in ascending or descending direction based on direction parameter.
     */

}
