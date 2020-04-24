package com.epam.pdp.recognitionservice.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Value("${rabbitmq.hostname}")
    private String rabbitMQhostname;

    @Value("${rabbitmq.port}")
    private Integer rabbitMQport;

    @Value("${rabbitmq.queue.text.recognition}")
    private String textRecognitionQueueName;

    @Value("${rabbitmq.exchange.topic.recognition.routing.key}")
    private String directDeadRecogExchangeName;

    @Value("${rabbitmq.exchange.direct.dead.recognition.routing.key}")
    private String recognitionDirectExchangeRoutingKey;

    @Value("${rabbitmq.queue.text.recognition.arg.ttl}")
    private Integer textRecognitionQueueTTL;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(rabbitMQhostname,rabbitMQport);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue textRecognitionQueue() {
        return QueueBuilder.durable(textRecognitionQueueName)
                .withArgument("x-dead-letter-exchange", directDeadRecogExchangeName)
                .withArgument("x-dead-letter-routing-key", recognitionDirectExchangeRoutingKey)
                .withArgument("x-message-ttl", textRecognitionQueueTTL)
                .build();
    }

}
