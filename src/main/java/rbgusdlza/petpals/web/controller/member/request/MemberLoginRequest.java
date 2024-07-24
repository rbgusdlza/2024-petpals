package rbgusdlza.petpals.web.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

}
