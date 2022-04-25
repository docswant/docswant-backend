package sju.capstone.docswant.core.error;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;
    private final List<FieldError> fields;

    private ErrorResponse(final ErrorCode code, final List<FieldError> fields) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.fields = fields;
    }

    private ErrorResponse(final ErrorCode code, final String message) {
        this.code = code.getCode();
        this.message = message;
        this.fields = new ArrayList<>();
    }

    private ErrorResponse(final ErrorCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.fields = new ArrayList<>();
    }

    public static ErrorResponse of(final ErrorCode code, BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, List<FieldError> errors) {
        return new ErrorResponse(code, errors);
    }

    public static ErrorResponse of(final ErrorCode code, String message) {
        return new ErrorResponse(code, message);
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of(e.getName(), value, e.getMessage());
        return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, errors);
    }

    @Getter
    public static class FieldError {
        private final String field;
        private final String value;
        private final String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> errors = new ArrayList<>();
            errors.add(new FieldError(field, value, reason));
            return errors;
        }

        public static List<FieldError> of(final BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()
                    ))
                    .collect(Collectors.toList());
        }
    }
}
