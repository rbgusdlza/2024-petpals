package rbgusdlza.petpals.web.service.popularity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rbgusdlza.petpals.domain.popularity.Popularity;
import rbgusdlza.petpals.domain.popularity.PopularityRepository;
import rbgusdlza.petpals.domain.post.Post;
import rbgusdlza.petpals.domain.post.PostRepository;
import rbgusdlza.petpals.domain.reaction.Reaction;
import rbgusdlza.petpals.domain.reaction.ReactionRepository;
import rbgusdlza.petpals.web.service.popularity.response.PopularityResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static rbgusdlza.petpals.domain.reaction.ReactionType.*;
import static rbgusdlza.petpals.domain.reaction.TargetType.*;

@ActiveProfiles("test")
@SpringBootTest
class PopularityServiceTest {

    @Autowired
    private PopularityService popularityService;

    @Autowired
    private PopularityRepository popularityRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void tearDown() {
        popularityRepository.deleteAllInBatch();
        reactionRepository.deleteAllInBatch();
        popularityRepository.deleteAllInBatch();
    }

    @DisplayName("인기도를 생성한다.")
    @Test
    void create() {
        //given
        Long postId = 1L;

        //when
        Long popularityId = popularityService.create(postId);

        //then
        assertThat(popularityId).isNotNull();
    }

    @DisplayName("인기도의 점수를 업데이트한다.")
    @Test
    void update() {
        //given
        Long postId = 1L;

        Reaction reaction = Reaction.of(1L, postId, POST, LIKE);
        reactionRepository.save(reaction);

        Popularity popularity = Popularity.of(postId, 0.0);
        popularityRepository.save(popularity);

        //when
        popularityService.update(postId);

        //then
        Popularity findPopularity = popularityRepository.findByPostId(postId).get();
        assertThat(findPopularity).isNotNull()
                .extracting("score")
                .isEqualTo(0.1);
    }

    @DisplayName("점수가 높은 순서대로 주어진 수만큼 인기도를 조회한다.")
    @Test
    void find() {
        //given
        Popularity popularity1 = Popularity.of(1L, 0.0);
        Popularity popularity2 = Popularity.of(2L, 2.0);
        Popularity popularity3 = Popularity.of(3L, 5.0);
        popularityRepository.saveAll(List.of(popularity1, popularity2, popularity3));

        //when
        List<PopularityResponse> response = popularityService.find(2);

        //then
        assertThat(response).hasSize(2)
                .extracting("postId", "score")
                .containsExactlyInAnyOrder(
                        tuple(3L, 5.0),
                        tuple(2L, 2.0)
                );
    }

    @DisplayName("게시물 아이디로 인기도를 삭제한다.")
    @Test
    void remove() {
        //given
        Post post = Post.of(1L, "title", "content");
        postRepository.save(post);
        Long postId = post.getId();

        Popularity popularity = Popularity.of(postId, 0.5);
        popularityRepository.save(popularity);

        //when
        Long removedPopularityId = popularityService.remove(postId);

        //then
        assertThat(popularityRepository.findByPostId(removedPopularityId)).isEmpty();
    }
}