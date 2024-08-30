package rbgusdlza.petpals.web.api.controller.post.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rbgusdlza.petpals.web.service.post.request.PostCursorServiceRequest;

@Getter
@NoArgsConstructor
public class PostCursorRequest {

    @NotNull
    private Long cursor;

    @Positive
    private int limit;

    @Builder
    private PostCursorRequest(Long cursor, int limit) {
        this.cursor = cursor;
        this.limit = limit;
    }

    public static PostCursorRequest of(Long cursor, int limit) {
        return PostCursorRequest.builder()
                .cursor(cursor)
                .limit(limit)
                .build();
    }

    public PostCursorServiceRequest toServiceRequest() {
        return PostCursorServiceRequest.of(cursor, limit);
    }
}
