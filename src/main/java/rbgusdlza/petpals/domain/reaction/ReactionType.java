package rbgusdlza.petpals.domain.reaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReactionType {

    LIKE("좋아요"),
    DISLIKE("싫어요"),
    ;

    private final String description;
}
