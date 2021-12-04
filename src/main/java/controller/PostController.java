package controller;

import model.Ping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    @GetMapping("/api/ping")
    public ResponseEntity<Ping> getPing(){
        Ping ping = new Ping();
        ping.setSuccess(true);
        ping.setStatus(HttpStatus.OK);
        ping.setStatusMessage("Service is running");
        return new ResponseEntity<>(ping, HttpStatus.OK);
    }
}
