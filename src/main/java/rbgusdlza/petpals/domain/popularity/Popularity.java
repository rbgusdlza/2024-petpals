package rbgusdlza.petpals.domain.popularity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import rbgusdlza.petpals.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SQLRestriction("entity_status = 'ACTIVE'")
@Table(indexes = @Index(name = "idx_score", columnList = "score"))
public class Popularity extends BaseEntity {

    private static final double LIKE_COUNT_WEIGHT = 0.1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "popularity_id")
    private Long id;

    private Long postId;
    private double score;

    @Builder
    private Popularity(Long postId, double score) {
        this.postId = postId;
        this.score = score;
    }

    public static Popularity of(Long postId, double score) {
        return Popularity.builder()
                .postId(postId)
                .score(score)
                .build();
    }

    public void updateScore(long likeCount) {
        this.score = likeCount * LIKE_COUNT_WEIGHT;
    }
}
