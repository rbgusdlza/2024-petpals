package rbgusdlza.petpals.domain.popularity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rbgusdlza.petpals.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Popularity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "popularity_id")
    private Long id;

    private Long postId;
    private int score;

    @Builder
    private Popularity(Long postId, int score) {
        this.postId = postId;
        this.score = score;
    }

    public static Popularity of(Long postId, int score) {
        return Popularity.builder()
                .postId(postId)
                .score(score)
                .build();
    }
}