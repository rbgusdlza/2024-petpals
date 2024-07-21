package rbgusdlza.petpals.web.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DUPLICATE_LOGIN_ID("중복된 아이디"),
    DUPLICATE_NICKNAME("중복된 닉네임"),
    ;

    private final String description;
}
