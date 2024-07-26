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
    private String fileName;
    private String filePath;

    @Builder
    private Photo(Long postId, String fileName, String filePath) {
        this.postId = postId;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public static Photo of(Long postId, String fileName, String filePath) {
        return Photo.builder()
                .postId(postId)
                .fileName(fileName)
                .filePath(filePath)
                .build();
    }
}
