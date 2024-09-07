package com.empiricus.service_email.core.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {
    @Bean
    NewTopic toEmailCreatedTopic() {
        return TopicBuilder.name("email-created")
                .partitions(4)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean
    NewTopic toEmailDeletedTopic() {
        return TopicBuilder.name("email-deleted")
                .partitions(4)
                .replicas(1)
                .compact()
                .build();
    }

}
