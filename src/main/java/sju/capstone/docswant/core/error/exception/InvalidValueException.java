package sju.capstone.docswant.core.error.exception;

import sju.capstone.docswant.core.error.ErrorCode;

public class InvalidValueException extends BusinessException {

    public InvalidValueException(String message, ErrorCode code) {
        super(message, code);
    }

    public InvalidValueException(ErrorCode code) {
        super(code);
    }

}
