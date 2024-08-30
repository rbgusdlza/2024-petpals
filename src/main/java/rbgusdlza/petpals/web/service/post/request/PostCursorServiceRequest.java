package rbgusdlza.petpals.web.service.post.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCursorServiceRequest {

    private Long targetId;
    private int limit;

    @Builder
    private PostCursorServiceRequest(Long targetId, int limit) {
        this.targetId = targetId;
        this.limit = limit;
    }

    public static PostCursorServiceRequest of(Long targetId, int limit) {
        return PostCursorServiceRequest.builder()
                .targetId(targetId)
                .limit(limit)
                .build();
    }
}
