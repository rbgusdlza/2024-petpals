package rbgusdlza.petpals.web.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DUPLICATE_LOGIN_ID("중복된 아이디"),
    DUPLICATE_NICKNAME("중복된 닉네임"),
    DUPLICATE_EMAIL("중복된 이메일"),
    SENDING_EMAIL_ERROR("이메일 전송 실패"),
    MEMBER_LOGIN_ERROR("매칭되는 사용자 정보 없음")
    ;

    private final String description;
}
