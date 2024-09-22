package rbgusdlza.petpals.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM Post p WHERE p.entity_status = 'ACTIVE' ORDER BY p.post_id DESC LIMIT :limit", nativeQuery = true)
    List<Post> findAllByOrderByIdDesc(@Param("limit") int limit);

    @Query(value = "SELECT * FROM Post p WHERE p.entity_status = 'ACTIVE' AND p.post_id < :postId ORDER BY p.post_id DESC LIMIT :limit", nativeQuery = true)
    List<Post> findAllByIdLessThanOrderByIdDesc(@Param("postId") Long postId, @Param("limit") int limit);

}
