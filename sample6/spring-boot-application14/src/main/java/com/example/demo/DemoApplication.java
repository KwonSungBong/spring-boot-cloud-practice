package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

import java.util.Random;

@EnableBinding(Source.class)
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private static final String[] data = new String[] {
            "foo", "bar"
    };

    @InboundChannelAdapter(channel = Source.OUTPUT, poller = @Poller(fixedDelay = "1000"))
    public MessageSource<String> sendTestData() {
        String value = data[RANDOM.nextInt(data.length)];
        return () ->
                new GenericMessage<>(value);
    }

}
