package rbgusdlza.petpals.domain.popularity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PopularityRepository extends JpaRepository<Popularity, Long> {

    Optional<Popularity> findByPostId(Long postId);

    @Query(value = "SELECT * FROM popularity p WHERE p.entity_status = 'ACTIVE' ORDER BY p.score DESC LIMIT :limit", nativeQuery = true)
    List<Popularity> findAllByOrderByScoreDesc(@Param("limit") int limit);

}
