package sju.capstone.docswant.common.format;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import sju.capstone.docswant.common.message.StatusMessage;

import java.time.LocalDateTime;

@Getter
public class ResponseFormat<T> {

    private final String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final ErrorFormat error;

    public ResponseFormat(final T data) {
        this.status = StatusMessage.SUCCESS;
        this.timestamp = LocalDateTime.now();
        this.data = data;
        this.error = null;
    }

    public ResponseFormat(final String status, final ErrorFormat error) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.data = null;
        this.error = error;
    }

    public static <T> ResponseFormat<T> of(final T data) {
        return new ResponseFormat<>(data);
    }

    public static ResponseFormat of(final String status, final ErrorFormat error) {
        return new ResponseFormat(status, error);
    }
}
