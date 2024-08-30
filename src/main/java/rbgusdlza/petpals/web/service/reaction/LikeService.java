package rbgusdlza.petpals.web.service.reaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rbgusdlza.petpals.domain.reaction.ReactionRepository;
import rbgusdlza.petpals.domain.reaction.ReactionType;
import rbgusdlza.petpals.domain.reaction.TargetType;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LikeService {

    private final ReactionRepository reactionRepository;

    public long countLike(Long targetId, TargetType targetType) {
        return reactionRepository.countByTargetIdAndTargetTypeAndType(targetId, targetType, ReactionType.LIKE);
    }
}
