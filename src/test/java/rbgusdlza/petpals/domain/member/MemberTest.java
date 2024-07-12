package rbgusdlza.petpals.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @DisplayName("사용자의 닉네임을 업데이트한다.")
    @Test
    void updateNickname() {
        //given
        Member member = Member.of("userA", "park", "1234", "member@gmail.com");

        //when
        member.updateNickname("kim");

        //then
        assertThat(member.getNickname()).isEqualTo("kim");
    }

    @DisplayName("사용자의 비밀번호를 업데이트한다.")
    @Test
    void updatePassword() {
        //given
        Member member = Member.of("userA", "park", "1234", "member@gmail.com");

        //when
        member.updatePassword("0123");

        //then
        assertThat(member.getPassword()).isEqualTo("0123");
    }
}