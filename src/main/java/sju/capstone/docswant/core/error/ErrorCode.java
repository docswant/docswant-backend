package sju.capstone.docswant.core.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    //common
    METHOD_NOT_ALLOWED("C001", 405, "Method Not Allowed"),
    INVALID_INPUT_VALUE("C002", 400, "Invalid Input Value"),
    INVALID_TYPE_VALUE("C003", 400, " Invalid Type Value"),
    ENTITY_NOT_FOUND("C004", 400, "Entity Not Found"),
    INTERNAL_SERVER_ERROR("C005", 500, "Internal Server Error")
    ;

    private final String code;
    private final int status;
    private final String message;
}
