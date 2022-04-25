package sju.capstone.docswant.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import sju.capstone.docswant.common.message.StatusMessage;
import sju.capstone.docswant.core.error.ErrorResponse;

import java.time.LocalDateTime;

@Getter
public class ResponseDto<T> {

    private final String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime timestamp;
    private final T data;
    private final ErrorResponse error;

    public ResponseDto(T data) {
        this.status = StatusMessage.SUCCESS;
        this.timestamp = LocalDateTime.now();
        this.data = data;
        this.error = null;
    }

    public ResponseDto(String status, ErrorResponse error) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.data = null;
        this.error = error;
    }

    public static <T> ResponseDto<T> of(T data) {
        return new ResponseDto<>(data);
    }

    public static ResponseDto of(String status, ErrorResponse error) {
        return new ResponseDto(status, error);
    }
}
