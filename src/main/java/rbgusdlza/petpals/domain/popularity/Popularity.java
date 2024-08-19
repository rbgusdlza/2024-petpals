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
}
