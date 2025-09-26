package rbgusdlza.petpals.global.messagebroker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rbgusdlza.petpals.global.messagebroker.request.LikeCacheUpdateMessage;
import rbgusdlza.petpals.web.service.reaction.LikeService;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeCacheUpdateListener {

    private final LikeService likeService;

    @Transactional
    @RabbitListener(queues = "like.queue")
    public void handleLikeCacheUpdateMessage(LikeCacheUpdateMessage message) {
        log.debug("Consume like message: {}", message);
        likeService.like(message.getMemberId(), message.getTargetId(), message.getTargetType());
    }
}
