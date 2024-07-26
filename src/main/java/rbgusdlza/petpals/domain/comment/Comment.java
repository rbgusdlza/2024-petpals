package rbgusdlza.petpals.domain.comment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rbgusdlza.petpals.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private Long postId;
    private String content;

    @Builder
    private Comment(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }

    public static Comment of(Long postId, String content) {
        return Comment.builder()
                .postId(postId)
                .content(content)
                .build();
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
