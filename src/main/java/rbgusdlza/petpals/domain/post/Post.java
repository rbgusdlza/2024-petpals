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
    private String title;
    private String content;

    @Builder
    private Post(Long memberId, String title, String content) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    public static Post of(Long memberId, String title, String content) {
        return Post.builder()
                .memberId(memberId)
                .title(title)
                .content(content)
                .build();
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
