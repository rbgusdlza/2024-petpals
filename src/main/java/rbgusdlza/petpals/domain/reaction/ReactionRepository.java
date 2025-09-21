package rbgusdlza.petpals.domain.reaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    Optional<Reaction> findByMemberIdAndTargetIdAndTargetTypeAndType(
            @Param("memberId") Long memberId,
            @Param("targetId") Long targetId,
            @Param("targetType") TargetType targetType,
            @Param("type") ReactionType type
    );

    long countByTargetIdAndTargetTypeAndType(Long targetId, TargetType targetType, ReactionType type);

    @Query("SELECT r.memberId FROM Reaction r WHERE r.targetId = :targetId AND r.targetType = :targetType AND r.type = :type")
    List<Long> findMemberIdsByTargetIdAndTargetTypeAndType(
            @Param("targetId") Long targetId,
            @Param("targetType") TargetType targetType,
            @Param("type") ReactionType type
    );
}
