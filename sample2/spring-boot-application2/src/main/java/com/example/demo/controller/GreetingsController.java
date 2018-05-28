package com.example.demo.controller;

import com.example.demo.domain.Greetings;
import com.example.demo.sender.GreetingsSender;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

    private final GreetingsSender greetingsSender;

    public GreetingsController(GreetingsSender greetingsSender) {
        this.greetingsSender = greetingsSender;
    }

    @GetMapping("/greetings")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void greetings(@RequestParam("message") String message) {
        Greetings greetings = Greetings.builder()
                .message(message)
                .timestamp(System.currentTimeMillis())
                .build();
        greetingsSender.sendGreeting(greetings);
    }

}
