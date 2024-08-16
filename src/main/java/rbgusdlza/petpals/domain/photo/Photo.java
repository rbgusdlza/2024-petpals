package rbgusdlza.petpals.domain.photo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rbgusdlza.petpals.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Photo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    private Long postId;
    private String photoName;

    @Builder
    private Photo(Long postId, String photoName) {
        this.postId = postId;
        this.photoName = photoName;
    }

    public static Photo of(Long postId, String photoName) {
        return Photo.builder()
                .postId(postId)
                .photoName(photoName)
                .build();
    }
}
