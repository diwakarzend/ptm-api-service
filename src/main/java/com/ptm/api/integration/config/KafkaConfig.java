
package com.ptm.api.integration.config;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration

@EnableKafka
public class KafkaConfig {

	@Bean

	@Primary
	public KafkaConsumer<String, String> kafkaconsumerConfig() {

		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "micro-service-group1");

		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);

		return kafkaConsumer;

	}

}
