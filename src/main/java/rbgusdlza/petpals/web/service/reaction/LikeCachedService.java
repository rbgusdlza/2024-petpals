package rbgusdlza.petpals.web.service.reaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import rbgusdlza.petpals.domain.reaction.TargetType;
import rbgusdlza.petpals.global.messagebroker.MessageService;
import rbgusdlza.petpals.global.messagebroker.request.LikeCacheUpdateMessage;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeCachedService {

    private static final String LIKE_EXCHANGE_NAME = "like.exchange";
    private static final String LIKE_ROUTING_KEY = "like.routing";

    private final LikeService likeService;
    private final RedisTemplate<String, Long> redisTemplate;
    private final MessageService messageService;

    public Boolean like(Long memberId, Long targetId, TargetType targetType) {
        String key = createCacheKey(targetId, targetType);
        Boolean isMember = redisTemplate.opsForSet().isMember(key, memberId);
        if (Boolean.TRUE.equals(isMember)) {
            return false;
        }

        redisTemplate.opsForSet().add(key, memberId);
        LikeCacheUpdateMessage message = LikeCacheUpdateMessage.of(memberId, targetId, targetType, key);
        messageService.sendMessage(LIKE_EXCHANGE_NAME, LIKE_ROUTING_KEY, message);
        return true;
    }

    public long countLike(Long targetId, TargetType targetType) {
        String key = createCacheKey(targetId, targetType);
        Long cacheCount = redisTemplate.opsForSet().size(key);
        if (cacheCount != null && cacheCount > 0) {
            return cacheCount;
        }

        long dbCount = likeService.countLike(targetId, targetType);
        if (dbCount > 0) {
            likeService.findLikedMemberIds(targetId, targetType)
                    .forEach(memberId -> redisTemplate.opsForSet().add(key, memberId));
        }
        return dbCount;
    }

    private String createCacheKey(Long targetId, TargetType targetType) {
        return "type:" + targetType.name() + ":id:" + targetId + ":like";
    }
}
