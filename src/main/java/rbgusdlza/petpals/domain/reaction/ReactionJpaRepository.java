package rbgusdlza.petpals.domain.reaction;

import org.springframework.data.jpa.repository.JpaRepository;
import rbgusdlza.petpals.domain.EntityStatus;

import java.util.Optional;

public interface ReactionJpaRepository extends JpaRepository<Reaction, Long> {

    Optional<Reaction> findByMemberIdAndTargetIdAndTargetTypeAndType(Long memberId, Long targetId, TargetType targetType, ReactionType type);

    long countByTargetIdAndTargetTypeAndTypeAndEntityStatus(Long targetId, TargetType targetType, ReactionType type, EntityStatus entityStatus);

}
