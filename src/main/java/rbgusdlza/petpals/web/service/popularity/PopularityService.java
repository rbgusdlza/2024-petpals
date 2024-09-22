package rbgusdlza.petpals.web.service.popularity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rbgusdlza.petpals.domain.popularity.Popularity;
import rbgusdlza.petpals.domain.popularity.PopularityRepository;
import rbgusdlza.petpals.web.error.PetPalsException;
import rbgusdlza.petpals.web.service.popularity.response.PopularityResponse;
import rbgusdlza.petpals.web.service.reaction.LikeService;

import java.util.List;

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

    @RabbitListener(queues = "like.queue")
    @Transactional
    public void update(Long postId) {
        Popularity popularity = popularityRepository.findByPostId(postId)
                .orElseThrow(() -> new PetPalsException(POPULARITY_NOT_FOUND));
        long likeCount = likeService.countLike(postId, POST);
        popularity.updateScore(likeCount);
    }

    public List<PopularityResponse> find(int limit) {
        return popularityRepository.findAllByOrderByScoreDesc(limit).stream()
                .map(PopularityResponse::of)
                .toList();
    }

    @Transactional
    public Long remove(Long postId) {
        Popularity popularity = popularityRepository.findByPostId(postId)
                .orElseThrow(() -> new PetPalsException(POPULARITY_NOT_FOUND));
        popularity.delete();
        return popularity.getId();
    }
}
