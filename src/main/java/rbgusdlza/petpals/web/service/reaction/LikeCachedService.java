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
        return updateLikeCacheFromDB(memberId, targetId, targetType, key);
    }

    public long countLike(Long targetId, TargetType targetType) {
        long likeCount = likeService.countLike(targetId, targetType);
        if (likeCount > 0) {
            updateLikeCacheFrom(targetId, targetType, createCacheKey(targetId, targetType));
        }
        return likeCount;
    }

    private Boolean updateLikeCacheFromDB(Long memberId, Long targetId, TargetType targetType, String key) {
        boolean isLiked = likeService.like(memberId, targetId, targetType);
        if (isLiked) {
            redisTemplate.opsForSet().add(key, memberId);
            return true;
        }
        updateLikeCacheFrom(targetId, targetType, key);
        return false;
    }

    private void updateLikeCacheFrom(Long targetId, TargetType targetType, String key) {
        LikeCacheUpdateMessage message = LikeCacheUpdateMessage.of(targetId, targetType, key);
        messageService.sendMessage(LIKE_EXCHANGE_NAME, LIKE_ROUTING_KEY, message);
    }

    private String createCacheKey(Long targetId, TargetType targetType) {
        return "type:" + targetType.name() + ":id:" + targetId + ":like";
    }
}
