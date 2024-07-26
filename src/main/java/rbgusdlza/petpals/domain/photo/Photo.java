package rbgusdlza.petpals.domain.photo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
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
    private MultipartFile image;

    @Builder
    private Photo(Long postId, MultipartFile image) {
        this.postId = postId;
        this.image = image;
    }

    public static Photo of(Long postId, MultipartFile image) {
        return Photo.builder()
                .postId(postId)
                .image(image)
                .build();
    }
}
