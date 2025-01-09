package rbgusdlza.petpals.web.service.reaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rbgusdlza.petpals.domain.reaction.Reaction;
import rbgusdlza.petpals.domain.reaction.ReactionRepository;
import rbgusdlza.petpals.domain.reaction.TargetType;

import static rbgusdlza.petpals.domain.reaction.ReactionType.LIKE;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LikeCachedService {

    private static final String LIKE_CACHE_NAME = "countLike";
    private static final String CACHE_MANAGER_NAME = "popularityCacheManager";

    private final ReactionRepository reactionRepository;
    private final CacheManager popularityCacheManager;

    @Transactional
    public Long like(Long memberId, Long targetId, TargetType targetType) {
        Reaction reaction = reactionRepository.findByMemberIdAndTargetIdAndTargetTypeAndTypeWithLock(
                memberId,
                targetId,
                targetType,
                LIKE
        ).orElseGet(() ->
                reactionRepository.save(
                        Reaction.of(
                                memberId,
                                targetId,
                                targetType,
                                LIKE
                        )
                )
        );
        updateLikeCache(targetId, targetType);
        return reaction.getId();
    }

    @Cacheable(cacheNames = LIKE_CACHE_NAME, key = "'type:' + #targetType.name() + ':id:' + #targetId + ':like:count'",
            cacheManager = CACHE_MANAGER_NAME)
    public long countLike(Long targetId, TargetType targetType) {
        return reactionRepository.countByTargetIdAndTargetTypeAndType(targetId, targetType, LIKE);
    }

    private void updateLikeCache(Long targetId, TargetType targetType) {
        long likeCount = countLike(targetId, targetType);
        Cache cache = popularityCacheManager.getCache(LIKE_CACHE_NAME);
        if (isExist(cache)) {
            String cacheKey = createCacheKey(targetId, targetType);
            cache.put(cacheKey, likeCount);
        }
    }

    private boolean isExist(Cache cache) {
        return cache != null;
    }

    private String createCacheKey(Long targetId, TargetType targetType) {
        return "type:" + targetType.name() + ":id:" + targetId + ":like:count";
    }
}
