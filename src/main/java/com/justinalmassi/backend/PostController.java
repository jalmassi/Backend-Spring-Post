package com.justinalmassi.backend;

import model.Ping;
import model.PostRequest;
import model.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import model.Post;
import service.PostService;

import java.util.List;

@RestController
public class PostController {

    PostService postService = new PostService();

    @GetMapping("/api/ping")
    public ResponseEntity<Ping> getPing(){
        Ping ping = new Ping();
        ping.setSuccess(true);
        ping.setStatus(HttpStatus.OK);
        ping.setStatusMessage("Service is running");
        return new ResponseEntity<>(ping, HttpStatus.OK);
    }

    /**
     * This method returns list of unique post for given post request.
     * @param postRequest : PostRequest class with attributes:
     *                    tags = to handle list of tags
     *                    sortBy = default is 'id' ,
     *                    Direction = default is 'asc'
     * @return If success, list of unique post
     *         else Error with HTTPStatus - BAD REQUEST and Message - Explaining error
     */
    @GetMapping("/api/posts")
    public ResponseEntity<PostResponse> getPosts(@RequestParam List<String> tag, PostRequest postRequest){
//        tag.forEach(System.out::println);
        postRequest.setTags(tag);
        PostResponse postResponse = new PostResponse();
//        Post validatePostRequest = postService.validateRequestParameter(postRequest);

//        if (validatePostRequest.getStatus() != null || validatePostRequest.getStatusMessage() != null) {
//            uniquePostResponse.setStatus(validatePostRequest.getStatus());
//            uniquePostResponse.setMessage(validatePostRequest.getStatusMessage());
//            return new ResponseEntity<>(uniquePostResponse, HttpStatus.OK);
//        }

        postResponse = postService.getPostServiceForRequest(postRequest);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }
}
