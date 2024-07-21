package rbgusdlza.petpals.web.service.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NicknameCheckResponse {

    private Boolean isDuplicated;

    @Builder
    private NicknameCheckResponse(boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }

    public static NicknameCheckResponse from(boolean isDuplicated) {
        return NicknameCheckResponse.builder()
                .isDuplicated(isDuplicated)
                .build();
    }
}
