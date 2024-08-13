package rbgusdlza.petpals.domain.reaction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rbgusdlza.petpals.domain.member.Member;
import rbgusdlza.petpals.domain.member.MemberRepository;
import rbgusdlza.petpals.domain.post.Post;
import rbgusdlza.petpals.domain.post.PostRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static rbgusdlza.petpals.domain.EntityStatus.ACTIVE;
import static rbgusdlza.petpals.domain.reaction.ReactionType.DISLIKE;
import static rbgusdlza.petpals.domain.reaction.ReactionType.LIKE;
import static rbgusdlza.petpals.domain.reaction.TargetType.COMMENT;
import static rbgusdlza.petpals.domain.reaction.TargetType.POST;

@ActiveProfiles("test")
@SpringBootTest
class ReactionRepositoryTest {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
        reactionRepository.deleteAllInBatch();
    }

    @DisplayName("사용자 아이디, 타겟 타입, 타겟 아이디, 반응 타입으로 해당하는 모든 반응을 조회한다.")
    @Test
    void findAllByMemberIdAndTargetIdAndTargetTypeAndType() {
        //given
        Member member = Member.of("userA", "park", "1234", "member@gmail.com");
        memberRepository.save(member);
        Long memberId = member.getId();

        Post post = Post.of(memberId, "title", "content");
        postRepository.save(post);
        Long postId = post.getId();

        Reaction reaction1 = Reaction.of(memberId, postId, POST, LIKE);
        Reaction reaction2 = Reaction.of(memberId, postId, POST, LIKE);
        Reaction reaction3 = Reaction.of(memberId, postId, COMMENT, LIKE);
        Reaction reaction4 = Reaction.of(memberId, postId, POST, DISLIKE);
        reactionRepository.saveAll(List.of(reaction1, reaction2, reaction3, reaction4));

        //when
        List<Reaction> reactions = reactionRepository.findAllByMemberIdAndTargetIdAndTargetTypeAndType(
                memberId, postId, POST, LIKE
        );

        //then
        assertThat(reactions).hasSize(2)
                .extracting("memberId", "targetId", "targetType", "type")
                .containsExactly(
                        tuple(memberId, postId, POST, LIKE),
                        tuple(memberId, postId, POST, LIKE)
                );
    }

    @DisplayName("타겟 아이디, 타겟 타입, 반응 타입, 제거 여부로 모든 반응의 개수를 반환한다.")
    @Test
    void countByTargetIdAndTargetTypeAndTypeAndEntityStatus() {
        //given
        Member member = Member.of("userA", "park", "1234", "member@gmail.com");
        memberRepository.save(member);
        Long memberId = member.getId();

        Post post = Post.of(memberId, "title", "content");
        postRepository.save(post);
        Long postId = post.getId();

        Reaction reaction1 = Reaction.of(memberId, postId, POST, LIKE);
        Reaction reaction2 = Reaction.of(memberId, postId, COMMENT, LIKE);
        Reaction reaction3 = Reaction.of(memberId, postId, COMMENT, DISLIKE);
        Reaction reaction4 = Reaction.of(memberId, postId, POST, DISLIKE);
        reactionRepository.saveAll(List.of(reaction1, reaction2, reaction3, reaction4));

        //when
        long count = reactionRepository.countByTargetIdAndTargetTypeAndTypeAndEntityStatus(
                postId, POST, LIKE, ACTIVE
        );

        //then
        assertThat(count).isEqualTo(1);
    }

}