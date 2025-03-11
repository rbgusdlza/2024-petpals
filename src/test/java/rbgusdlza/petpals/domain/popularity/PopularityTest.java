package rbgusdlza.petpals.domain.popularity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class PopularityTest {

    private static final double EPSILON = 0.00001;

    @DisplayName("점수를 업데이트한다.")
    @Test
    void updateScore() {
        //given
        Popularity popularity = Popularity.of(1L, 0.5);

        //when
        popularity.updateScore(3);

        //then
        assertThat(popularity.getScore()).isCloseTo(0.3, within(EPSILON));
    }

}