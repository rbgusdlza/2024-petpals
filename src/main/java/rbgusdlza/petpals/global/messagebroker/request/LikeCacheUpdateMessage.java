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

    private Long memberId;
    private Long targetId;
    private TargetType targetType;
    private String cacheKey;

    @Builder
    private LikeCacheUpdateMessage(Long memberId, Long targetId, TargetType targetType, String cacheKey) {
        this.memberId = memberId;
        this.targetId = targetId;
        this.targetType = targetType;
        this.cacheKey = cacheKey;
    }

    public static LikeCacheUpdateMessage of(Long memberId, Long targetId, TargetType targetType, String cacheKey) {
        return LikeCacheUpdateMessage.builder()
                .memberId(memberId)
                .targetId(targetId)
                .targetType(targetType)
                .cacheKey(cacheKey)
                .build();
    }
}
