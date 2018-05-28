package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @EnableBinding(Sink.class)
    static class TestSink {

        private final Log logger = LogFactory.getLog(getClass());

        @StreamListener("test-sink")
        public void receive(String payload) {
            logger.info("Data received: " + payload);
        }
    }

    public interface Sink {
        @Input("test-sink")
        SubscribableChannel sampleSink();
    }

}
