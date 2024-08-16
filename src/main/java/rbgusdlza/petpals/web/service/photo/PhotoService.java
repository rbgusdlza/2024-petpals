package rbgusdlza.petpals.web.service.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rbgusdlza.petpals.domain.photo.*;
import rbgusdlza.petpals.global.util.FileNameGenerator;
import rbgusdlza.petpals.web.service.photo.request.PhotoRegisterServiceRequest;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final PhotoDetailsRepository photoDetailsRepository;
    private final PhotoHandler photoHandler;
    private final PhotoProcessor photoProcessor;

    @Transactional
    public Long register(PhotoRegisterServiceRequest request) {
        MultipartFile imageFile = request.getImageFile();
        String storeFileName = getStoreFileName(imageFile);
        photoHandler.saveImageFile(imageFile, storeFileName);

        Long postId = request.getPostId();
        Photo photo = photoProcessor.createPhoto(postId, imageFile);
        photoRepository.save(photo);

        Long photoId = photo.getId();
        PhotoDetails photoDetails = photoProcessor.createPhotoDetails(photoId, storeFileName);
        photoDetailsRepository.save(photoDetails);
        return photoId;
    }

    private String getStoreFileName(MultipartFile imageFile) {
        String originalFileName = imageFile.getOriginalFilename();
        return FileNameGenerator.generateStoreFileNameFrom(originalFileName);
    }
}
