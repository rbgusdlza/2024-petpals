package rbgusdlza.petpals.web.service.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rbgusdlza.petpals.domain.member.Member;

@Getter
@NoArgsConstructor
public class MemberJoinServiceRequest {

    private String joinId;
    private String nickname;
    private String password;
    private String email;

    public Member toEntity() {
        return Member.builder()
                .loginId(joinId)
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();
    }

    @Builder
    private MemberJoinServiceRequest(String joinId, String nickname, String password, String email) {
        this.joinId = joinId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }
}
