package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableBinding(DemoApplication.PolledProcessor.class)
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    public static final ExecutorService exec = Executors.newSingleThreadExecutor();

    @Bean
    public ApplicationRunner runner(PollableMessageSource input, MessageChannel output) {
        return args -> {
            System.out.println("Send some messages to topic polledConsumerIn and receive from polledConsumerOut");
            System.out.println("Messages will be processed one per second");
            exec.execute(() -> {
                boolean result = false;
                while (true) {
                    // this is where we poll for a message, process it, and send a new one
                    result = input.poll(m -> {
                        String payload = (String) m.getPayload();
                        System.out.println("Received: " + payload);
                        output.send(MessageBuilder.withPayload(payload.toUpperCase())
                                .copyHeaders(m.getHeaders())
                                .build());
                    }, new ParameterizedTypeReference<String>() { });

                    try {
                        Thread.sleep(1_000);
                    }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                    if (result) {
                        System.out.println("Success");
                    }
                }
            });
        };
    }

    public interface PolledProcessor {
        @Input
        PollableMessageSource input();
        @Output
        MessageChannel output();
    }
}
