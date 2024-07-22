package rbgusdlza.petpals.web.service.member.response;

import lombok.Builder;
import lombok.Getter;
import rbgusdlza.petpals.domain.member.Member;

@Getter
public class MemberResponse {

    private Long id;
    private String loginId;
    private String nickname;
    private String email;

    @Builder
    private MemberResponse(Long id, String loginId, String nickname, String email) {
        this.id = id;
        this.loginId = loginId;
        this.nickname = nickname;
        this.email = email;
    }

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .build();
    }
}
