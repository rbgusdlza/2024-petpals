package rbgusdlza.petpals.web.error;

import lombok.Getter;

@Getter
public class PetPalsException extends RuntimeException {

    private final ErrorCode errorCode;

    public PetPalsException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}