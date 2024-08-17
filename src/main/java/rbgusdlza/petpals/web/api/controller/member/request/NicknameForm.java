package rbgusdlza.petpals.web.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rbgusdlza.petpals.web.service.member.request.NicknameServiceForm;

@Getter
@Setter
@NoArgsConstructor
public class NicknameForm {

    @NotBlank
    private String nickname;

    @Builder
    private NicknameForm(String nickname) {
        this.nickname = nickname;
    }

    public static NicknameForm of(String nickname) {
        return NicknameForm.builder()
                .nickname(nickname)
                .build();
    }

    public NicknameServiceForm toServiceForm() {
        return NicknameServiceForm.builder()
                .nickname(nickname)
                .build();
    }
}
