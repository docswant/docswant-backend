package sju.capstone.docswant.core.error.exception;

import sju.capstone.docswant.core.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorCode code) {
        super(code);
    }

}
