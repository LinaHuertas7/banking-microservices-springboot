package com.banking.spring.ms_accounts.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitMQConfig {
    public static final String CLIENT_CREATED_QUEUE = "client.created.queue";
    public static final String EXCHANGE = "banking.exchange";
    public static final String CLIENT_CREATED_KEY = "client.created";

    @Bean
    MessageConverter jsonMessageConverter() {
        JacksonJsonMessageConverter converter = new JacksonJsonMessageConverter();
        converter.setAlwaysConvertToInferredType(true);
        return converter;
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    Queue clientCreatedQueue() {
        return new Queue(CLIENT_CREATED_QUEUE, true);
    }

    @Bean
    TopicExchange bankingExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Binding binding(Queue clientCreatedQueue, TopicExchange bankingExchange) {
        return BindingBuilder.bind(clientCreatedQueue)
                .to(bankingExchange)
                .with(CLIENT_CREATED_KEY);
    }

    @Bean
    Queue clientQueryRequestQueue() {
        return QueueBuilder.durable("client.query.request.queue").build();
    }

    @Bean
    Queue accountReplyQueue() {
        return QueueBuilder.durable("account.reply.queue").build();
    }

    @Bean
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());

        factory.setDefaultRequeueRejected(false);

        return factory;
    }
}