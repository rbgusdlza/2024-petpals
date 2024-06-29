package rbgusdlza.petpals.domain.member.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {

    @NotEmpty
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]{5,10}$")
    private String loginId;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{10,20}$")
    private String password;
}
