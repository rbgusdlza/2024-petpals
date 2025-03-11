package rbgusdlza.petpals.global.messagebroker.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rbgusdlza.petpals.domain.reaction.TargetType;

@Getter
@Setter
@NoArgsConstructor
public class LikeCacheUpdateMessage {

    private Long targetId;
    private TargetType targetType;
    private String cacheKey;

    @Builder
    private LikeCacheUpdateMessage(Long targetId, TargetType targetType, String cacheKey) {
        this.targetId = targetId;
        this.targetType = targetType;
        this.cacheKey = cacheKey;
    }

    public static LikeCacheUpdateMessage of(Long targetId, TargetType targetType, String cacheKey) {
        return LikeCacheUpdateMessage.builder()
                .targetId(targetId)
                .targetType(targetType)
                .cacheKey(cacheKey)
                .build();
    }
}
