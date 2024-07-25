package rbgusdlza.petpals.web.service.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rbgusdlza.petpals.domain.member.Member;
import rbgusdlza.petpals.domain.member.MemberRepository;
import rbgusdlza.petpals.web.service.member.request.LoginIdServiceForm;
import rbgusdlza.petpals.web.service.member.request.MemberJoinServiceRequest;
import rbgusdlza.petpals.web.service.member.request.MemberLoginServiceRequest;
import rbgusdlza.petpals.web.service.member.response.LoginIdCheckResponse;
import rbgusdlza.petpals.web.service.member.response.MemberResponse;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("사용자를 등록한다.")
    @Test
    void join() {
        //given
        MemberJoinServiceRequest request = MemberJoinServiceRequest.of("userA", "park", "1234", "member@gmail.com");

        //when
        MemberResponse response = memberService.join(request);

        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getLoginId()).isEqualTo("userA");
        assertThat(response.getNickname()).isEqualTo("park");
        assertThat(response.getEmail()).isEqualTo("member@gmail.com");
    }

    @DisplayName("로그인한 사용자를 반환한다.")
    @Test
    void login() {
        //given
        Member member = Member.of("userA", "park", "1234", "member@gmail.com");
        memberRepository.save(member);

        MemberLoginServiceRequest request = MemberLoginServiceRequest.of("userA", "1234");

        //when
        MemberResponse response = memberService.login(request);

        //then
        assertThat(response).isNotNull()
                .extracting("loginId", "nickname", "email")
                .containsExactly("userA", "park", "member@gmail.com");
    }

    @DisplayName("로그인 아이디가 중복되었는지 확인한다.")
    @Test
    void isLoginIdDuplicate() {
        //given
        Member member = Member.of("userA", "park", "1234", "member@gmail.com");
        memberRepository.save(member);

        LoginIdServiceForm form = LoginIdServiceForm.of("userA");

        //when
        LoginIdCheckResponse response = memberService.isLoginIdDuplicate(form);

        //then
        assertThat(response.getIsDuplicated()).isTrue();
    }

    @DisplayName("로그인 아이디가 중복되었는지 확인한다.")
    @Test
    void isLoginIdDuplicate2() {
        //given
        Member member = Member.of("userA", "park", "1234", "member@gmail.com");
        memberRepository.save(member);

        LoginIdServiceForm form = LoginIdServiceForm.of("userB");

        //when
        LoginIdCheckResponse response = memberService.isLoginIdDuplicate(form);

        //then
        assertThat(response.getIsDuplicated()).isFalse();
    }
}