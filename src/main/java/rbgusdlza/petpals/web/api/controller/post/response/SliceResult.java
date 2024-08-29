package rbgusdlza.petpals.web.controller.post.response;

import lombok.Builder;
import lombok.Getter;
import rbgusdlza.petpals.web.service.post.response.PostResponse;

import java.util.List;

@Getter
public class SliceResult {

    private static final long NONE_KEY = -1L;

    private List<PostResponse> posts;
    private Long cursor;
    private int limit;

    @Builder
    private SliceResult(List<PostResponse> posts, Long cursor, int limit) {
        this.posts = posts;
        this.cursor = cursor;
        this.limit = limit;
    }

    public static SliceResult of(List<PostResponse> posts, int limit) {
        return SliceResult.builder()
                .posts(posts)
                .cursor(getLastCursor(posts))
                .limit(limit)
                .build();
    }

    private static Long getLastCursor(List<PostResponse> posts) {
        return posts.stream()
                .map(PostResponse::getId)
                .min(Long::compareTo)
                .orElse(NONE_KEY);
    }
}
