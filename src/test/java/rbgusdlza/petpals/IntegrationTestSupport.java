package rbgusdlza.petpals;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import rbgusdlza.petpals.domain.photo.PhotoHandler;

@ActiveProfiles("test")
@SpringBootTest
@EnableAutoConfiguration(exclude = {
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class,
        RabbitAutoConfiguration.class
})
@TestPropertySource(properties = "spring.session.store-type=none")
public abstract class IntegrationTestSupport {

    @MockBean
    protected PhotoHandler photoHandler;

    @MockBean
    protected RabbitTemplate rabbitTemplate;

    @MockBean
    protected ConnectionFactory connectionFactory;

    @MockBean
    protected SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory;
}
