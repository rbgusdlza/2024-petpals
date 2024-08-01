package rbgusdlza.petpals.web.service.post.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rbgusdlza.petpals.domain.post.Post;

@Getter
@NoArgsConstructor
public class PostRegisterServiceRequest {

    private Long memberId;
    private String title;
    private String content;

    @Builder
    private PostRegisterServiceRequest(Long memberId, String title, String content) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    public Post toEntity() {
        return Post.of(memberId, title, content);
    }
}
