package rbgusdlza.petpals.web.service.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rbgusdlza.petpals.domain.photo.*;
import rbgusdlza.petpals.global.util.FileNameGenerator;
import rbgusdlza.petpals.web.error.PetPalsException;

import static rbgusdlza.petpals.web.error.ErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final PhotoDetailsRepository photoDetailsRepository;
    private final PhotoHandler photoHandler;

    @Transactional
    public Long register(Long postId, MultipartFile imageFile) {
        String originalFileName = imageFile.getOriginalFilename();
        String storeFileName = FileNameGenerator.generateStoreFileNameFrom(originalFileName);
        photoHandler.saveImageFile(imageFile, storeFileName);

        Photo photo = Photo.of(postId, originalFileName);
        photoRepository.save(photo);

        Long photoId = photo.getId();
        PhotoDetails photoDetails = PhotoDetails.of(photoId, storeFileName);
        photoDetailsRepository.save(photoDetails);
        return photoId;
    }

    public PhotoWithDetails getPhotoWithDetails(Long postId) {
        Photo photo = findPhotoBy(postId);
        PhotoDetails photoDetails = findPhotoDetailsBy(photo.getId());
        return PhotoWithDetails.of(photo, photoDetails);
    }

    private Photo findPhotoBy(Long postId) {
        return photoRepository.findByPostId(postId)
                .orElseThrow(() -> new PetPalsException(FINDING_PHOTO_ERROR));
    }

    private PhotoDetails findPhotoDetailsBy(Long photoId) {
        return photoDetailsRepository.findByPhotoId(photoId)
                .orElseThrow(() -> new PetPalsException(FINDING_PHOTO_ERROR));
    }
}
