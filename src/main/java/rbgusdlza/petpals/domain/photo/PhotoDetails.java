package rbgusdlza.petpals.domain.photo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PhotoDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_details_id")
    private Long id;

    private Long photoId;
    private String storeFileName;
    private String filePath;

    @Builder
    private PhotoDetails(Long photoId, String storeFileName, String filePath) {
        this.photoId = photoId;
        this.storeFileName = storeFileName;
        this.filePath = filePath;
    }

    public PhotoDetails of(Long photoId, String storeFileName, String filePath) {
        return PhotoDetails.builder()
                .photoId(photoId)
                .storeFileName(storeFileName)
                .filePath(filePath)
                .build();
    }
}
