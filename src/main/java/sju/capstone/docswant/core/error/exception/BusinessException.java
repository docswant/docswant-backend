package sju.capstone.docswant.core.error.exception;

import lombok.Getter;
import sju.capstone.docswant.core.error.ErrorCode;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorCode code;

    public BusinessException(final String message, final ErrorCode code) {
        super(message);
        this.code = code;
    }

    public BusinessException(final ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
