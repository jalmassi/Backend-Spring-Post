package com.justinalmassi.backend.controller;

import com.justinalmassi.backend.model.Ping;
import com.justinalmassi.backend.model.PostRequest;
import com.justinalmassi.backend.model.PostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.justinalmassi.backend.model.Post;
import com.justinalmassi.backend.service.PostService;

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

    @GetMapping("/api/posts")
    public ResponseEntity<PostResponse> getPosts(@RequestParam List<String> tag, PostRequest postRequest){
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
