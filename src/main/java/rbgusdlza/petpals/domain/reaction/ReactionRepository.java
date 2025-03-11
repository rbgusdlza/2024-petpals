package rbgusdlza.petpals.domain.reaction;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Reaction r WHERE r.memberId = :memberId AND r.targetId = :targetId AND r.targetType = :targetType AND r.type = :type")
    Optional<Reaction> findByMemberIdAndTargetIdAndTargetTypeAndTypeWithLock(
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
