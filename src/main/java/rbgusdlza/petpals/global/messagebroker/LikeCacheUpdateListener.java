package rbgusdlza.petpals.global.messagebroker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import rbgusdlza.petpals.global.messagebroker.request.LikeCacheUpdateMessage;
import rbgusdlza.petpals.web.service.reaction.LikeService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeCacheUpdateListener {

    private final LikeService likeService;
    private final RedisTemplate<String, Long> redisTemplate;

    @RabbitListener(queues = "like.queue")
    public void handleLikeCacheUpdateMessage(LikeCacheUpdateMessage message) {
        Long targetId = message.getTargetId();
        String cacheKey = message.getCacheKey();

        List<Long> likedMemberIds = likeService.findLikedMemberIds(targetId, message.getTargetType());
        redisTemplate.opsForSet().add(cacheKey, likedMemberIds.toArray(Long[]::new));
    }
}
