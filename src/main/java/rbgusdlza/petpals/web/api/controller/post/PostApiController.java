package rbgusdlza.petpals.web.api.controller.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import rbgusdlza.petpals.web.api.ApiResponse;
import rbgusdlza.petpals.web.api.controller.post.request.PostCursorRequest;
import rbgusdlza.petpals.web.api.controller.post.response.SliceResult;
import rbgusdlza.petpals.web.service.post.PostService;
import rbgusdlza.petpals.web.service.post.response.PostResponse;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/list")
    public ApiResponse<SliceResult> list(@RequestBody PostCursorRequest request) {
        List<PostResponse> posts = postService.findAfter(request.toServiceRequest());
        return ApiResponse.ok(SliceResult.of(posts, request.getLimit()));
    }
}
