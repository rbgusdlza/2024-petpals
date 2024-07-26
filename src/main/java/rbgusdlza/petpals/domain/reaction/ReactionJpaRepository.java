package rbgusdlza.petpals.domain.reaction;

import org.springframework.data.jpa.repository.JpaRepository;
import rbgusdlza.petpals.domain.EntityStatus;

import java.util.List;
import java.util.Optional;

public interface ReactionJpaRepository extends JpaRepository<Reaction, Long> {

    List<Reaction> findAllByMemberIdAndTargetIdAndTargetTypeAndType(Long memberId, Long targetId, TargetType targetType, ReactionType type);

    long countByTargetIdAndTargetTypeAndTypeAndEntityStatus(Long targetId, TargetType targetType, ReactionType type, EntityStatus entityStatus);

}
