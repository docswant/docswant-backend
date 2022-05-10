package sju.capstone.docswant.core.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    //common
    METHOD_NOT_ALLOWED("C001", HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed"),
    INVALID_INPUT_VALUE("C002", HttpStatus.BAD_REQUEST, "Invalid Input Value"),
    INVALID_TYPE_VALUE("C003", HttpStatus.BAD_REQUEST, " Invalid Type Value"),
    ENTITY_NOT_FOUND("C004", HttpStatus.BAD_REQUEST, "Entity Not Found"),
    INTERNAL_SERVER_ERROR("C005", HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    MEDIA_TYPE_NOT_SUPPORTED("C006", HttpStatus.BAD_REQUEST, "Media Type Not Supported"),

    //auth
    HANDLE_AUTHENTICATION_FAILED("A001", HttpStatus.UNAUTHORIZED, "Authentication Failed"),
    HANDLE_ACCESS_DENIED("A002", HttpStatus.FORBIDDEN, "Access Denied"),
    INVALID_TOKEN("A003", HttpStatus.BAD_REQUEST, "Invalid Token"),
    ;

    private final String code;
    private final HttpStatus status;
    private final String message;
}
