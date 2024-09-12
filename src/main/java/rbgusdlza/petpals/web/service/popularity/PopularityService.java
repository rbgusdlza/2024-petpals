package rbgusdlza.petpals.web.service.popularity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rbgusdlza.petpals.domain.popularity.Popularity;
import rbgusdlza.petpals.domain.popularity.PopularityRepository;
import rbgusdlza.petpals.web.error.PetPalsException;
import rbgusdlza.petpals.web.service.reaction.LikeService;

import static rbgusdlza.petpals.domain.reaction.TargetType.*;
import static rbgusdlza.petpals.web.error.ErrorCode.POPULARITY_NOT_FOUND;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PopularityService {

    private final PopularityRepository popularityRepository;
    private final LikeService likeService;

    @Transactional
    public Long create(Long postId) {
        Popularity popularity = Popularity.of(postId, 0.0);
        popularityRepository.save(popularity);
        return popularity.getId();
    }

    @Transactional
    public Long update(Long postId) {
        Popularity popularity = popularityRepository.findByPostId(postId)
                .orElseThrow(() -> new PetPalsException(POPULARITY_NOT_FOUND));
        long likeCount = likeService.countLike(postId, POST);
        popularity.updateScore(likeCount);
        return popularity.getId();
    }
}
