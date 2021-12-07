package com.justinalmassi.backend.controller;

import com.justinalmassi.backend.error_handling.BadRequestException;
import com.justinalmassi.backend.error_handling.DirectionException;
import com.justinalmassi.backend.error_handling.PostNotFoundException;
import com.justinalmassi.backend.error_handling.SortByException;
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
import java.util.Optional;

@RestController
public class PostController {

    PostService postService = new PostService();

    @GetMapping("/api/ping")
    public ResponseEntity<Ping> getPing() {
        Ping ping = new Ping();
        ping.setSuccess(true);
        ping.setStatus(HttpStatus.OK);
        ping.setStatusMessage("Service is running");
        return new ResponseEntity<>(ping, HttpStatus.OK);
    }

    @GetMapping("/api/posts")
    public ResponseEntity<PostResponse> getPosts(@RequestParam List<String> tag, @RequestParam(required = false) Optional<String> sortBy, @RequestParam(required = false) Optional<String> direction, PostRequest postRequest) {
        try {
            postRequest.setSortBy(sortBy.orElseGet(() -> "ID").toUpperCase());
            postRequest.setDirection(direction.orElseGet(() -> "asc"));
            if(tag.size() == 0) throw new BadRequestException();
            if(sortBy != null && postRequest.getSortBy().isEmpty()) throw new SortByException();
            if(direction != null && postRequest.getDirection().isEmpty()) throw new DirectionException();
            postRequest.setTags(tag);
            PostResponse postResponse = new PostResponse();

            postResponse = postService.getPostService(postRequest);
            return new ResponseEntity<>(postResponse, HttpStatus.OK);
        } catch (PostNotFoundException e) {
            throw new PostNotFoundException();
        }
    }
}
