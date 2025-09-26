package rbgusdlza.petpals.web.api.controller.reaction;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import rbgusdlza.petpals.global.messagebroker.MessageService;
import rbgusdlza.petpals.web.api.ApiResponse;
import rbgusdlza.petpals.web.service.reaction.LikeCachedService;

import static rbgusdlza.petpals.domain.reaction.TargetType.POST;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/post/popular")
@RestController
public class LikePopularApiController {

    private static final String EXCHANGE_NAME = "popularity.exchange";
    private static final String ROUTING_KEY = "popularity.routing";

    private final MessageService messageService;
    private final LikeCachedService likeCachedService;

    @PutMapping("/{postId}/like")
    public ApiResponse<Boolean> like(@PathVariable Long postId,
                                     HttpSession session) {
        if (doesUserLogin(session)) {
            return ApiResponse.fail("로그인 상태에서만 좋아요가 가능합니다.");
        }
        Boolean isLiked = likeCachedService.like(getMemberIdFrom(session), postId, POST);
        messageService.sendMessage(EXCHANGE_NAME, ROUTING_KEY, postId);
        return ApiResponse.ok(isLiked);
    }

    @GetMapping("/{postId}/count-like")
    public ApiResponse<Long> countLike(@PathVariable Long postId) {
        return ApiResponse.ok(likeCachedService.countLike(postId, POST));
    }

    private Long getMemberIdFrom(HttpSession session) {
        return (Long) session.getAttribute("id");
    }

    private boolean doesUserLogin(HttpSession session) {
        return session == null || session.getAttribute("id") == null;
    }
}
