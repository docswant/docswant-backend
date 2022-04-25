package sju.capstone.docswant.core.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import sju.capstone.docswant.common.format.ErrorFormat;
import sju.capstone.docswant.common.format.ResponseFormat;
import sju.capstone.docswant.common.message.StatusMessage;
import sju.capstone.docswant.core.error.exception.BusinessException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseFormat> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException. message = {}", e.getMessage());
        final ErrorFormat error = ErrorFormat.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseFormat.of(StatusMessage.FAIL, error));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ResponseFormat> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException. propertyName = {}, requiredType = {}", e.getPropertyName(), e.getRequiredType());
        final ErrorFormat error = ErrorFormat.of(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseFormat.of(StatusMessage.FAIL, error));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ResponseFormat> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException. method = {}", e.getMethod());
        final ErrorFormat error = ErrorFormat.of(ErrorCode.METHOD_NOT_ALLOWED);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ResponseFormat.of(StatusMessage.FAIL, error));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<ResponseFormat> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("handleHttpMediaTypeNotSupportedException. contentType = {}", e.getContentType());
        final ErrorFormat error = ErrorFormat.of(ErrorCode.MEDIA_TYPE_NOT_SUPPORTED);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseFormat.of(StatusMessage.FAIL, error));
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ResponseFormat> handleBusinessException(final BusinessException e) {
        log.warn("handleBusinessException. message = {}", e.getMessage());
        final ErrorCode code = e.getCode();
        final ErrorFormat error = ErrorFormat.of(code);
        return ResponseEntity.status(code.getStatus()).body(ResponseFormat.of(StatusMessage.ERROR, error));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseFormat> handleException(Exception e) {
        log.error("handleException. message = {}", e.getMessage());
        final ErrorFormat error = ErrorFormat.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseFormat.of(StatusMessage.ERROR, error));
    }
}
