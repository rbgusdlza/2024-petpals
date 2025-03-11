package rbgusdlza.petpals.web.service.reaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rbgusdlza.petpals.domain.reaction.Reaction;
import rbgusdlza.petpals.domain.reaction.ReactionRepository;
import rbgusdlza.petpals.domain.reaction.TargetType;

import java.util.List;

import static rbgusdlza.petpals.domain.reaction.ReactionType.LIKE;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LikeService {

    private final ReactionRepository reactionRepository;

    @Transactional
    public Boolean like(Long memberId, Long targetId, TargetType targetType) {
        Reaction reaction = reactionRepository.findByMemberIdAndTargetIdAndTargetTypeAndTypeWithLock(
                memberId,
                targetId,
                targetType,
                LIKE
        ).orElseGet(() ->
                reactionRepository.save(
                        Reaction.of(
                                memberId,
                                targetId,
                                targetType,
                                LIKE
                        )
                )
        );
        return reaction.getId() != null;
    }

    public long countLike(Long targetId, TargetType targetType) {
        return reactionRepository.countByTargetIdAndTargetTypeAndType(targetId, targetType, LIKE);
    }

    public List<Long> findLikedMemberIds(Long targetId, TargetType targetType) {
        return reactionRepository.findMemberIdsByTargetIdAndTargetTypeAndType(targetId, targetType, LIKE);
    }
}
