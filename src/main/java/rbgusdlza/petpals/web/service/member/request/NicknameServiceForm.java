package rbgusdlza.petpals.web.service.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NicknameServiceForm {

    private String nickname;

    @Builder
    private NicknameServiceForm(String nickname) {
        this.nickname = nickname;
    }
}
