package rbgusdlza.petpals.domain.popularity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rbgusdlza.petpals.IntegrationTestSupport;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class PopularityRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private PopularityRepository popularityRepository;

    @AfterEach
    void tearDown() {
        popularityRepository.deleteAllInBatch();
    }

    @DisplayName("게시물 아이디로 인기도를 조회한다.")
    @Test
    void findByPostId() {
        //given
        Popularity popularity = Popularity.of(1L, 0.5);
        popularityRepository.save(popularity);

        //when
        Popularity findPopularity = popularityRepository.findByPostId(1L).get();

        //then
        assertThat(findPopularity).isNotNull()
                .extracting("postId", "score")
                .containsExactlyInAnyOrder(1L, 0.5);
    }

    @DisplayName("점수가 높은 순서대로 주어진 수만큼 인기도를 조회한다.")
    @Test
    void findAllByOrderByScoreDesc() {
        //given
        Popularity popularity1 = Popularity.of(1L, 0.1);
        Popularity popularity2 = Popularity.of(2L, 0.2);
        Popularity popularity3 = Popularity.of(3L, 0.3);
        popularityRepository.saveAll(List.of(popularity1, popularity2, popularity3));

        //when
        List<Popularity> popularities = popularityRepository.findAllByOrderByScoreDesc(2);

        //then
        assertThat(popularities).hasSize(2)
                .extracting("postId", "score")
                .containsExactly(
                        tuple(3L, 0.3),
                        tuple(2L, 0.2)
                );
    }
}