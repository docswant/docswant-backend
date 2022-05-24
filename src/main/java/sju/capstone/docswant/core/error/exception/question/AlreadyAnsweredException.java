package sju.capstone.docswant.core.error.exception.question;

import sju.capstone.docswant.core.error.ErrorCode;
import sju.capstone.docswant.core.error.exception.BusinessException;

public class AlreadyAnsweredException extends BusinessException {

    public AlreadyAnsweredException(String message) {
        super(message);
    }

    public AlreadyAnsweredException(String message, ErrorCode code) {
        super(message, code);
    }

    public AlreadyAnsweredException(ErrorCode code) {
        super(code);
    }
}
