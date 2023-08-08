package com.springretry.retryController;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@SuppressWarnings("all")
public class retryController {

    public int i = 0;

    @GetMapping("/get")
    @Retryable(value = {SQLException.class, IOException.class}  ,maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    public String getMessage() {
        i++;
        RestTemplate rt = new RestTemplate();
        System.out.println(i);
        rt.getForObject("http://localhost:9090/demo", Object.class);
        return "Message received successfully.";
    }


    @Recover
    public String getMassage(){
        return "The url does not connect after 5 times try";
    }
}
