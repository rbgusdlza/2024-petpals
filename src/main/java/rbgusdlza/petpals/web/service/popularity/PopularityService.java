package rbgusdlza.petpals.web.service.popularity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rbgusdlza.petpals.domain.popularity.Popularity;
import rbgusdlza.petpals.domain.popularity.PopularityRepository;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PopularityService {

    private final PopularityRepository popularityRepository;

    @Transactional
    public Long create(Long postId) {
        Popularity popularity = Popularity.of(postId, 0.0);
        popularityRepository.save(popularity);
        return popularity.getId();
    }
}
