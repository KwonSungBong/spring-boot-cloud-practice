package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;

import javax.annotation.processing.Processor;
import java.util.concurrent.atomic.AtomicBoolean;

@EnableBinding(Processor.class)
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @EnableBinding(Source.class)
    static class TestSource {

        private AtomicBoolean semaphore = new AtomicBoolean(true);

        @Bean
        @InboundChannelAdapter(channel = "test-source", poller = @Poller(fixedDelay = "1000"))
        public MessageSource<String> sendTestData() {
            return () ->
                    new GenericMessage<>(this.semaphore.getAndSet(!this.semaphore.get()) ? "foo" : "bar");
        }
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

    public interface Source {
        @Output("test-source")
        MessageChannel sampleSource();
    }

}
