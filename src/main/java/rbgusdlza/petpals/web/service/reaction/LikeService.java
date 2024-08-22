package rbgusdlza.petpals.web.service.reaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rbgusdlza.petpals.domain.reaction.ReactionRepository;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;

    public long
}
