package rbgusdlza.petpals.web.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PetPalsException extends RuntimeException {

    private final ErrorCode errorCode;

}
