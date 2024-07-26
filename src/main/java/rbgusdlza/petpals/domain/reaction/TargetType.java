package rbgusdlza.petpals.domain.reaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TargetType {

    POST("게시물"),
    COMMENT("댓글"),
    ;

    private final String description;
}
