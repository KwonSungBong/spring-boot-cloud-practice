# spring-boot-cloud-stream-practice

https://github.com/ignacioSuay/spring-stream

https://github.com/spring-cloud/spring-cloud-stream-samples/tree/master/source-samples/dynamic-destination-source

docker exec -it kafka_kafka_1 /opt/kafka/bin/kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic polledConsumerIn

docker exec -it kafka_kafka_1 /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic polledConsumerOut --from-beginning

.................................................

application6 실행

https://github.com/spring-cloud/spring-cloud-stream-binder-kafka/blob/master/spring-cloud-stream-binder-kafka-docs/src/main/asciidoc/overview.adoc




