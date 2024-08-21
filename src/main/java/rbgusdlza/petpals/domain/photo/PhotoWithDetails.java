package rbgusdlza.petpals.domain.photo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PhotoWithDetails {

    private Photo photo;
    private PhotoDetails photoDetails;

    @Builder
    private PhotoWithDetails(Photo photo, PhotoDetails photoDetails) {
        this.photo = photo;
        this.photoDetails = photoDetails;
    }

    public static PhotoWithDetails of(Photo photo, PhotoDetails photoDetails) {
        return PhotoWithDetails.builder()
                .photo(photo)
                .photoDetails(photoDetails)
                .build();
    }
}
