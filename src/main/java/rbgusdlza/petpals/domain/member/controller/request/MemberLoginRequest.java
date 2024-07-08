package rbgusdlza.petpals.domain.member.controller.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

}
