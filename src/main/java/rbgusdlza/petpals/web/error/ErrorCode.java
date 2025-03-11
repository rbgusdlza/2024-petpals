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
    MEMBER_LOGIN_ERROR("매칭되는 사용자 정보 없음"),
    MEMBER_JOIN_ERROR("입력값과 중복된 사용자가 이미 존재"),
    POST_NOT_FOUND("매칭되는 게시물 없음"),
    PHOTO_NOT_FOUND("매칭되는 사진 없음"),
    PHOTO_DETAILS_NOT_FOUND("매칭되는 사진 정보 없음"),
    LIKE_NOT_FOUND("매칭되는 좋아요 없음"),
    POPULARITY_NOT_FOUND("매칭되는 인기도 없음"),
    IMAGE_IS_EMPTY("업로드된 이미지 파일 없음");

    private final String description;
}
