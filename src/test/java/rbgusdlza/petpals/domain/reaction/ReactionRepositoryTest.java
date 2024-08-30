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
import java.util.Optional;

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

    @DisplayName("사용자 아이디, 타겟 타입, 타겟 아이디, 반응 타입으로 해당하는 반응을 조회한다.")
    @Test
    void findByMemberIdAndTargetIdAndTargetTypeAndType() {
        //given
        Member member = Member.of("userA", "park", "1234", "member@gmail.com");
        memberRepository.save(member);
        Long memberId = member.getId();

        Post post = Post.of(memberId, "title", "content");
        postRepository.save(post);
        Long postId = post.getId();

        Reaction reaction1 = Reaction.of(memberId, postId, POST, LIKE);
        Reaction reaction2 = Reaction.of(memberId, postId, COMMENT, LIKE);
        Reaction reaction3 = Reaction.of(memberId, postId, POST, DISLIKE);
        reactionRepository.saveAll(List.of(reaction1, reaction2, reaction3));

        //when
        Reaction findReaction = reactionRepository.findByMemberIdAndTargetIdAndTargetTypeAndType(
                memberId, postId, POST, LIKE
        ).get();

        //then
        assertThat(findReaction).isNotNull()
                .extracting("memberId", "targetId", "targetType", "type")
                .containsExactly(memberId, postId, POST, LIKE);
    }

    @DisplayName("타겟 아이디, 타겟 타입, 반응 타입으로 모든 반응의 개수를 반환한다.")
    @Test
    void countByTargetIdAndTargetTypeAndTypeAndEntityStatus() {
        //given
        Member member1 = Member.of("userA", "park", "1234", "member1@gmail.com");
        Member member2 = Member.of("userB", "kim", "2345", "member2@gmail.com");
        memberRepository.saveAll(List.of(member1, member2));
        Long member1Id = member1.getId();
        Long member2Id = member2.getId();

        Post post = Post.of(member1Id, "title", "content");
        postRepository.save(post);
        Long postId = post.getId();

        Reaction reaction1 = Reaction.of(member1Id, postId, POST, LIKE);
        Reaction reaction2 = Reaction.of(member1Id, postId, COMMENT, LIKE);
        Reaction reaction3 = Reaction.of(member2Id, postId, COMMENT, DISLIKE);
        Reaction reaction4 = Reaction.of(member2Id, postId, POST, DISLIKE);
        reactionRepository.saveAll(List.of(reaction1, reaction2, reaction3, reaction4));

        //when
        long count = reactionRepository.countByTargetIdAndTargetTypeAndType(postId, POST, LIKE);

        //then
        assertThat(count).isEqualTo(1);
    }

}