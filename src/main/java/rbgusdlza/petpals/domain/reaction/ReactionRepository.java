package rbgusdlza.petpals.domain.reaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    Optional<Reaction> findByMemberIdAndTargetIdAndTargetTypeAndType(Long memberId, Long targetId, TargetType targetType, ReactionType type);

    long countByTargetIdAndTargetTypeAndType(Long targetId, TargetType targetType, ReactionType type);

}
