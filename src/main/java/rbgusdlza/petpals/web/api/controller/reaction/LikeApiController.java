package rbgusdlza.petpals.web.api.controller.reaction;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import rbgusdlza.petpals.web.api.ApiResponse;
import rbgusdlza.petpals.web.service.reaction.LikeService;

import static rbgusdlza.petpals.domain.reaction.TargetType.POST;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class LikeApiController {

    private final LikeService likeService;

    @PostMapping("/{postId}/like")
    public ApiResponse<Long> like(@PathVariable Long postId,
                                  HttpSession session) {
        Long memberId = getMemberIdFrom(session);
        return ApiResponse.ok(likeService.like(memberId, postId, POST));
    }

    @GetMapping("/{postId}/count-like")
    public ApiResponse<Long> countLike(@PathVariable Long postId) {
        return ApiResponse.ok(likeService.countLike(postId, POST));
    }

    private Long getMemberIdFrom(HttpSession session) {
        return (Long) session.getAttribute("id");
    }
}
