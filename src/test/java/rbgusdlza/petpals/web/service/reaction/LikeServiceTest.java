package rbgusdlza.petpals.web.service.reaction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rbgusdlza.petpals.IntegrationTestSupport;
import rbgusdlza.petpals.domain.reaction.Reaction;
import rbgusdlza.petpals.domain.reaction.ReactionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rbgusdlza.petpals.domain.reaction.ReactionType.LIKE;
import static rbgusdlza.petpals.domain.reaction.TargetType.POST;

class LikeServiceTest extends IntegrationTestSupport {

    @Autowired
    private LikeService likeService;

    @Autowired
    private ReactionRepository reactionRepository;

    @AfterEach
    void tearDown() {
        reactionRepository.deleteAllInBatch();
    }

    @DisplayName("사용자 아이디, 타겟 아이디, 타겟 타입으로 좋아요를 생성한다.")
    @Test
    void like() {
        //given //when
        Boolean likeId = likeService.like(1L, 1L, POST);

        //then
        assertThat(likeId).isNotNull();
    }

    @DisplayName("타겟 아이디, 타겟 타입으로 좋아요 개수를 조회한다.")
    @Test
    void countLike() {
        //given
        Reaction reaction1 = Reaction.of(1L, 1L, POST, LIKE);
        Reaction reaction2 = Reaction.of(2L, 1L, POST, LIKE);
        Reaction reaction3 = Reaction.of(3L, 2L, POST, LIKE);
        reactionRepository.saveAll(List.of(reaction1, reaction2, reaction3));

        //when
        long likeCount = likeService.countLike(1L, POST);

        //then
        assertThat(likeCount).isEqualTo(2);
    }
}