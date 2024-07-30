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
    private String storeFileName;

    @Builder
    private Photo(Long postId, String storeFileName) {
        this.postId = postId;
        this.storeFileName = storeFileName;
    }

    public static Photo of(Long postId, String storeFileName) {
        return Photo.builder()
                .postId(postId)
                .storeFileName(storeFileName)
                .build();
    }
}
