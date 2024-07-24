package rbgusdlza.petpals.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("로그인 아이디로 사용자를 조회한다.")
    @Test
    void findByLoginId() {
        //given
        Member member = Member.of("userA", "park", "1234", "member@gmail.com");
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findByLoginId("userA");

        //then
        assertThat(findMember).isNotNull()
                .extracting("loginId", "nickname", "email")
                .containsExactly("userA", "park", "member@gmail.com");
    }

    @DisplayName("닉네임으로 사용자를 조회한다.")
    @Test
    void findByNickname() {
        //given
        Member member = Member.of("userA", "park", "1234", "member@gmail.com");
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findByNickname("park");

        //then
        assertThat(findMember).isNotNull()
                .extracting("loginId", "nickname", "email")
                .containsExactly("userA", "park", "member@gmail.com");
    }

    @DisplayName("이메일로 사용자를 조회한다.")
    @Test
    void findByEmail() {
        //given
        Member member = Member.of("userA", "park", "1234", "member@gmail.com");
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findByEmail("member@gmail.com");

        //then
        assertThat(findMember).isNotNull()
                .extracting("loginId", "nickname", "email")
                .containsExactly("userA", "park", "member@gmail.com");
    }

    @DisplayName("로그인 아이디와 비밀번호로 사용자를 조회한다.")
    @Test
    void findByLoginIdAndEncryptedPassword() {
        //given
        Member member = Member.of("userA", "park", "1234", "member@gmail.com");
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findByLoginIdAndEncryptedPassword("userA", "1234");

        //then
        assertThat(findMember).isNotNull()
                .extracting("loginId", "nickname", "email")
                .containsExactly("userA", "park", "member@gmail.com");
    }

    @DisplayName("로그인 아이디와 비밀번호로 사용자를 조회할 때, 해당하는 사용자가 없으면 null을 반환한다.")
    @Test
    void findByLoginIdAndEncryptedPasswordWithoutMatchingUser() {
        //given
        Member member = Member.of("userA", "park", "1234", "member@gmail.com");
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findByLoginIdAndEncryptedPassword("user", "1234");

        //then
        assertThat(findMember).isNull();
    }
}