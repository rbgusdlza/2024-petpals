package rbgusdlza.petpals.web.service.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NicknameCheckResponse {

    private Boolean isDuplicated;

    public static NicknameCheckResponse from(Boolean isDuplicated) {
        return NicknameCheckResponse.builder()
                .isDuplicated(isDuplicated)
                .build();
    }

    @Builder
    private NicknameCheckResponse(Boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }
}
