package rbgusdlza.petpals.web.service.reaction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import rbgusdlza.petpals.domain.reaction.Reaction;
import rbgusdlza.petpals.domain.reaction.ReactionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static rbgusdlza.petpals.domain.reaction.ReactionType.LIKE;
import static rbgusdlza.petpals.domain.reaction.TargetType.POST;

@ActiveProfiles("test")
@SpringBootTest
class LikeCachedServiceTest {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private LikeCachedService likeCachedService;

    @MockBean
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @AfterEach
    void tearDown() {
        reactionRepository.deleteAllInBatch();
    }

    @DisplayName("사용자 아이디, 타겟 아이디, 타겟 타입으로 좋아요를 생성한다.")
    @Test
    void like() {
        //given //when
        Long likeId = likeCachedService.like(1L, 1L, POST);

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

        when(cacheManager.getCache("countLike")).thenReturn(cache);
        given(cache.get("type:POST:id:1:like:count", Long.class)).willReturn(null);

        //when
        long likeCount = likeCachedService.countLike(1L, POST);

        //then
        assertThat(likeCount).isEqualTo(2);
    }
}