package rbgusdlza.petpals.domain.popularity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PopularityRepository extends JpaRepository<Popularity, Long> {

    Optional<Popularity> findByPostId(Long postId);
}
