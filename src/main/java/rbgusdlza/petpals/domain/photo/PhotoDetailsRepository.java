package rbgusdlza.petpals.domain.photo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhotoDetailsRepository extends JpaRepository<PhotoDetails, Long> {

    Optional<PhotoDetails> findByPhotoId(Long photoId);
}
