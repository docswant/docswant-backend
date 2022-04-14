package sju.capstone.docswant.core.error.exception;

import sju.capstone.docswant.core.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message, ErrorCode code) {
        super(message, code);
    }

    public EntityNotFoundException(ErrorCode code) {
        super(code);
    }

}
