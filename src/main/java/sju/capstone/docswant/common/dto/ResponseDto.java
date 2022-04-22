package sju.capstone.docswant.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseDto<T> {

    private final String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    public ResponseDto(String message, T data) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }

    public static <T> ResponseDto<T> of(String message) {
        return new ResponseDto<>(message, null);
    }

    public static <T> ResponseDto<T> of(String message, T data) {
        return new ResponseDto<>(message, data);
    }
}
