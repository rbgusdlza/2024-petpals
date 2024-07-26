package rbgusdlza.petpals.domain.post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rbgusdlza.petpals.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private Long memberId;
    private String content;

    @Builder
    private Post(Long memberId, String content) {
        this.memberId = memberId;
        this.content = content;
    }

    public static Post of(Long memberId, String content) {
        return Post.builder()
                .memberId(memberId)
                .content(content)
                .build();
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
