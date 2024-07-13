package rbgusdlza.petpals.web.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_LOGIN_ID_LENGTH("로그인 아이디 길이 오류", "아이디는 8자에서 15자 사이여야 합니다."),
    INVALID_LOGIN_ID_FORMAT("로그인 아이디 포맷 오류", "아이디는 영어 대문자, 소문자, 숫자로만 구성되어야 합니다."),
    ;


    private final String description;
    private final String message;
}
