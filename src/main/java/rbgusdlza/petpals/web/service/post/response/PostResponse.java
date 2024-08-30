package rbgusdlza.petpals.web.service.post.response;

import lombok.Builder;
import lombok.Getter;
import rbgusdlza.petpals.domain.photo.PhotoWithDetails;
import rbgusdlza.petpals.domain.post.Post;

@Getter
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String uploadFileName;
    private String storeFileName;
    private long likeCount;

    @Builder
    private PostResponse(Long id, String title, String content, String uploadFileName, String storeFileName, long likeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.likeCount = likeCount;
    }

    public static PostResponse of(Post post, PhotoWithDetails photoWithDetails, long likeCount) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .uploadFileName(photoWithDetails.getPhoto().getPhotoName())
                .storeFileName(photoWithDetails.getPhotoDetails().getStoreFileName())
                .likeCount(likeCount)
                .build();
    }
}
