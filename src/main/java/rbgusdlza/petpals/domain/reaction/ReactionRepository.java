package rbgusdlza.petpals.domain.reaction;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<Reaction> findByMemberIdAndTargetIdAndTargetTypeAndType(Long memberId, Long targetId, TargetType targetType, ReactionType type);

    long countByTargetIdAndTargetTypeAndType(Long targetId, TargetType targetType, ReactionType type);

}
