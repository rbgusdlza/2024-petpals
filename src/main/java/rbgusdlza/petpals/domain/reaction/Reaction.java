package rbgusdlza.petpals.domain.reaction;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import rbgusdlza.petpals.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("entity_status = 'ACTIVE'")
@Entity
@Table(
        name = "reaction",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_reaction_member_target_type",
                        columnNames = {"memberId", "targetId", "targetType", "type"}
                )
        }
)
public class Reaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reaction_id")
    private Long id;
    private Long memberId;
    private Long targetId;

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    @Enumerated(EnumType.STRING)
    private ReactionType type;

    @Builder
    private Reaction(Long memberId, Long targetId, TargetType targetType, ReactionType type) {
        this.memberId = memberId;
        this.targetId = targetId;
        this.targetType = targetType;
        this.type = type;
    }

    public static Reaction of(Long memberId, Long targetId, TargetType targetType, ReactionType type) {
        return Reaction.builder()
                .memberId(memberId)
                .targetId(targetId)
                .targetType(targetType)
                .type(type)
                .build();
    }
}
