package rbgusdlza.petpals.web.service.popularity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rbgusdlza.petpals.domain.popularity.PopularityRepository;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class PopularityServiceTest {

    @Autowired
    private PopularityService popularityService;

    @Autowired
    private PopularityRepository popularityRepository;

    @AfterEach
    void tearDown() {
        popularityRepository.deleteAllInBatch();
    }

    @DisplayName("인기도를 생성한다.")
    @Test
    void createPopularity() {
        //given
        Long postId = 1L;

        //when
        Long popularityId = popularityService.create(postId);

        //then
        assertThat(popularityId).isNotNull();
    }
}