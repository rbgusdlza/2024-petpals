package rbgusdlza.petpals.web.service.popularity.response;

import lombok.Builder;
import lombok.Getter;
import rbgusdlza.petpals.domain.popularity.Popularity;

@Getter
public class PopularityResponse {

    private Long postId;
    private double score;

    @Builder
    private PopularityResponse(Long postId, double score) {
        this.postId = postId;
        this.score = score;
    }

    public static PopularityResponse of(Popularity popularity) {
        return PopularityResponse.builder()
                .postId(popularity.getPostId())
                .score(popularity.getScore())
                .build();
    }
}
