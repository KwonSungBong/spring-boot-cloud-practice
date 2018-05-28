# spring-boot-cloud-stream-practice

https://github.com/ignacioSuay/spring-stream

docker exec -it kafka_kafka_1 /opt/kafka/bin/kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic polledConsumerIn

docker exec -it kafka_kafka_1 /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic polledConsumerOut --from-beginning


