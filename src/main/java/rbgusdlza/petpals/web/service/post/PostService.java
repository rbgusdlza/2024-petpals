package rbgusdlza.petpals.web.service.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rbgusdlza.petpals.domain.photo.PhotoWithDetails;
import rbgusdlza.petpals.domain.post.Post;
import rbgusdlza.petpals.domain.post.PostRepository;
import rbgusdlza.petpals.web.error.PetPalsException;
import rbgusdlza.petpals.web.service.photo.PhotoService;
import rbgusdlza.petpals.web.service.popularity.PopularityService;
import rbgusdlza.petpals.web.service.post.request.PostCursorServiceRequest;
import rbgusdlza.petpals.web.service.post.request.PostRegisterServiceRequest;
import rbgusdlza.petpals.web.service.post.response.PostResponse;
import rbgusdlza.petpals.web.service.reaction.LikeService;

import java.util.List;
import java.util.Objects;

import static rbgusdlza.petpals.domain.reaction.TargetType.POST;
import static rbgusdlza.petpals.web.error.ErrorCode.POST_NOT_FOUND;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PhotoService photoService;
    private final PopularityService popularityService;
    private final LikeService likeService;

    @Transactional
    public Long register(PostRegisterServiceRequest request) {
        Post post = request.toPost();
        postRepository.save(post);

        Long postId = post.getId();
        popularityService.create(postId);
        photoService.register(postId, request.getImageFile());
        return postId;
    }

    public PostResponse findBy(Long postId) {
        Post post = getPost(postId);
        PhotoWithDetails photoWithDetails = photoService.findPhotoWithDetailsBy(postId);
        long likeCount = likeService.countLike(postId, POST);
        return PostResponse.of(post, photoWithDetails, likeCount);
    }

    public List<PostResponse> findRecentPosts(int limit) {
        return postRepository.findAllByOrderByIdDesc(limit).stream()
                .map(post -> findBy(post.getId()))
                .toList();
    }

    public List<PostResponse> findAfter(PostCursorServiceRequest request) {
        return postRepository.findAllByIdLessThanOrderByIdDesc(request.getTargetId(), request.getLimit()).stream()
                .map(post -> findBy(post.getId()))
                .toList();
    }

    public List<PostResponse> findPopularPosts(int limit) {
        return popularityService.find(limit).stream()
                .map(popularityResponse -> findBy(popularityResponse.getPostId()))
                .toList();
    }

    public boolean isPostCreatedByMember(Long postId, Long memberId) {
        Post post = getPost(postId);
        return Objects.equals(post.getMemberId(), memberId);
    }

    @Transactional
    public Long remove(Long postId) {
        popularityService.remove(postId);
        photoService.remove(postId);
        Post post = getPost(postId);
        post.delete();
        return post.getId();
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PetPalsException(POST_NOT_FOUND));
    }
}
