package rbgusdlza.petpals.web.service.reaction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.transaction.annotation.Transactional;
import rbgusdlza.petpals.IntegrationTestSupport;
import rbgusdlza.petpals.domain.reaction.Reaction;
import rbgusdlza.petpals.domain.reaction.ReactionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static rbgusdlza.petpals.domain.reaction.ReactionType.LIKE;
import static rbgusdlza.petpals.domain.reaction.TargetType.POST;

@Transactional
class LikeCachedServiceTest extends IntegrationTestSupport {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private LikeCachedService likeCachedService;

    @MockBean
    private RedisTemplate<String, Long> redisTemplate;

    @AfterEach
    void tearDown() {
        reactionRepository.deleteAllInBatch();
    }

    @DisplayName("사용자 아이디, 타겟 아이디, 타겟 타입으로 좋아요를 생성한다.")
    @Test
    void like() {
        //given
        SetOperations<String, Long> setOps = mock(SetOperations.class);  // SetOperations 모킹
        given(redisTemplate.opsForSet()).willReturn(setOps);  // opsForSet()이 setOps를 반환하도록 설정
        given(setOps.isMember(anyString(), anyLong())).willReturn(true);  // isMember() 모킹

        //when
        Boolean isLiked = likeCachedService.like(1L, 1L, POST);

        //then
        assertThat(isLiked).isFalse();
    }

    @DisplayName("타겟 아이디, 타겟 타입으로 좋아요 개수를 조회한다.")
    @Test
    void countLike() {
        //given
        Reaction reaction1 = Reaction.of(1L, 1L, POST, LIKE);
        Reaction reaction2 = Reaction.of(2L, 1L, POST, LIKE);
        Reaction reaction3 = Reaction.of(3L, 2L, POST, LIKE);
        reactionRepository.saveAll(List.of(reaction1, reaction2, reaction3));

        SetOperations<String, Long> setOps = mock(SetOperations.class);  // SetOperations 모킹
        given(redisTemplate.opsForSet()).willReturn(setOps);  // opsForSet()이 setOps를 반환하도록 설정
        given(setOps.size(anyString())).willReturn(null);  // size() 모킹

        //when
        long likeCount = likeCachedService.countLike(1L, POST);

        //then
        assertThat(likeCount).isEqualTo(2);
    }
}