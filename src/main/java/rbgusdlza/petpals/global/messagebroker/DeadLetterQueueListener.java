package rbgusdlza.petpals.global.messagebroker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rbgusdlza.petpals.global.messagebroker.request.LikeCacheUpdateMessage;

@Slf4j
@Component
public class DeadLetterQueueListener {

    @RabbitListener(queues = "like.dlq.queue")
    public void handleDlqMessage(LikeCacheUpdateMessage message) {
        log.error("===== Dead Letter Message Received =====");
        log.error("Cache Key   : {}", message.getCacheKey());
        log.error("Target ID   : {}", message.getTargetId());
        log.error("Target Type : {}", message.getTargetType().name());
        log.error("========================================");
    }
}
