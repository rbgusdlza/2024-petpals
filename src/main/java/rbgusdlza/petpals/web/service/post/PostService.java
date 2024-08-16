package rbgusdlza.petpals.web.service.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rbgusdlza.petpals.domain.post.Post;
import rbgusdlza.petpals.domain.post.PostRepository;
import rbgusdlza.petpals.web.service.photo.PhotoService;
import rbgusdlza.petpals.web.service.photo.request.PhotoRegisterServiceRequest;
import rbgusdlza.petpals.web.service.post.request.PostRegisterServiceRequest;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PhotoService photoService;

    @Transactional
    public Long register(PostRegisterServiceRequest postRequest) {
        Post post = postRequest.toPost();
        postRepository.save(post);

        Long postId = post.getId();
        PhotoRegisterServiceRequest photoRequest = postRequest.toPhotoRegisterServiceRequest(postId);
        photoService.register(photoRequest);
        return postId;
    }
}
