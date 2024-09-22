package rbgusdlza.petpals;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import rbgusdlza.petpals.domain.photo.PhotoHandler;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {

    @MockBean
    protected CacheManager cacheManager;

    @MockBean
    protected PhotoHandler photoHandler;
}
