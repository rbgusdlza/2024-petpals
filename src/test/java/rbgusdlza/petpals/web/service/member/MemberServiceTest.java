package rbgusdlza.petpals.web.service.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rbgusdlza.petpals.domain.member.MemberRepository;
import rbgusdlza.petpals.web.service.member.request.MemberJoinServiceRequest;
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
    void joinMember() {
        //given
        MemberJoinServiceRequest request = MemberJoinServiceRequest.of("userA", "park", "1234", "member@gmail.com");

        //when
        MemberResponse response = memberService.joinMember(request);

        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getLoginId()).isEqualTo("userA");
        assertThat(response.getNickname()).isEqualTo("park");
        assertThat(response.getEmail()).isEqualTo("member@gmail.com");
    }
}