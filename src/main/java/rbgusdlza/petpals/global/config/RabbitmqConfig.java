package rbgusdlza.petpals.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class RabbitmqConfig {

    private static final String POPULARITY_EXCHANGE_NAME = "popularity.exchange";
    private static final String POPULARITY_QUEUE_NAME = "popularity.queue";
    private static final String POPULARITY_ROUTING_KEY = "popularity.routing";

    private static final String LIKE_EXCHANGE_NAME = "like.exchange";
    private static final String LIKE_QUEUE_NAME = "like.queue";
    private static final String LIKE_ROUTING_KEY = "like.routing";

    private static final String LIKE_DLQ_EXCHANGE_NAME = "like.dlq.exchange";
    private static final String LIKE_DLQ_QUEUE_NAME = "like.dlq.queue";
    private static final String LIKE_DLQ_ROUTING_KEY = "like.dlq.routing";

    @Bean
    DirectExchange popularityExchange() {
        return new DirectExchange(POPULARITY_EXCHANGE_NAME);
    }

    @Bean
    DirectExchange likeExchange() {
        return new DirectExchange(LIKE_EXCHANGE_NAME);
    }

    @Bean
    DirectExchange likeDlqExchange() {
        return new DirectExchange(LIKE_DLQ_EXCHANGE_NAME);
    }

    @Bean
    Queue popularityQueue() {
        return new Queue(POPULARITY_QUEUE_NAME);
    }

    @Bean
    Queue likeQueue() {
        return QueueBuilder.durable(LIKE_QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", LIKE_DLQ_EXCHANGE_NAME)
                .withArgument("x-dead-letter-routing-key", LIKE_DLQ_ROUTING_KEY)
                .withArgument("x-message-ttl", 10000)
                .build();
    }

    @Bean
    Queue likeDlqQueue() {
        return new Queue(LIKE_DLQ_QUEUE_NAME);
    }

    @Bean
    Binding popularityBinding(DirectExchange popularityExchange, Queue popularityQueue) {
        return BindingBuilder.bind(popularityQueue).to(popularityExchange).with(POPULARITY_ROUTING_KEY);
    }


    @Bean
    Binding likeBinding(DirectExchange likeExchange, Queue likeQueue) {
        return BindingBuilder.bind(likeQueue).to(likeExchange).with(LIKE_ROUTING_KEY);
    }

    @Bean
    Binding likeDlqBinding(Queue likeDlqQueue, DirectExchange likeDlqExchange) {
        return BindingBuilder.bind(likeDlqQueue).to(likeDlqExchange).with(LIKE_DLQ_ROUTING_KEY);
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                // 메시지 전송 실패 시 로그에 기록
                log.error("Message failed to send: {}, Correlation Data: {}", cause, correlationData);
            }
        });

        rabbitTemplate.setReturnsCallback(returned ->
                // 메시지가 라우팅되지 못한 경우 로그에 기록
                log.warn("Message returned: {}, Exchange: {}, Routing Key: {}",
                        returned.getMessage(), returned.getExchange(), returned.getRoutingKey())
        );
        return rabbitTemplate;
    }

    @Bean
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());

        factory.setAdviceChain(RetryInterceptorBuilder.stateless()
                .maxAttempts(3)
                .backOffOptions(1000, 2.0, 10000)
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build());

        return factory;
    }
}