package rbgusdlza.petpals.web.service.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginServiceRequest {

    private String loginId;
    private String encryptedPassword;

    @Builder
    private MemberLoginServiceRequest(String loginId, String encryptedPassword) {
        this.loginId = loginId;
        this.encryptedPassword = encryptedPassword;
    }

    public static MemberLoginServiceRequest of(String loginId, String encryptedPassword) {
        return MemberLoginServiceRequest.builder()
                .loginId(loginId)
                .encryptedPassword(encryptedPassword)
                .build();
    }
}
