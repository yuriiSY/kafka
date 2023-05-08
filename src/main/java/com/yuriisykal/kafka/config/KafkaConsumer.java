package com.yuriisykal.kafka.config;

import com.yuriisykal.kafka.Message;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumer {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String,Object> consumerConfig() {
        Map<String,Object> prop = new HashMap<>();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        return prop;
    }


    @Bean
    public ConsumerFactory<String, Message> consumerFactory() {
        JsonDeserializer<Message> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(
                consumerConfig(),
                new StringDeserializer(),
                jsonDeserializer);
    }

    @Bean
    public KafkaListenerContainerFactory<
            ConcurrentMessageListenerContainer<String,Message>> factory(
                    ConsumerFactory<String,Message> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String,Message> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
}
