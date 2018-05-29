package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

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

//    @InboundChannelAdapter(channel = Source.OUTPUT, poller = @Poller(fixedRate = "1000"))
//    public Message<?> generate() {
//        String value = data[RANDOM.nextInt(data.length)];
//        System.out.println("Sending: " + value);
//        return MessageBuilder.withPayload(value)
//                .setHeader("partitionKey", value)
//                .build();
//    }

    private AtomicBoolean semaphore = new AtomicBoolean(true);

    @InboundChannelAdapter(channel = Source.OUTPUT, poller = @Poller(fixedDelay = "1000"))
    public MessageSource<String> sendTestData() {
        return () ->
                new GenericMessage<>(this.semaphore.getAndSet(!this.semaphore.get()) ? "foo" : "bar");
    }

}
