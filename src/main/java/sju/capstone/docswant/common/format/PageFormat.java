package sju.capstone.docswant.common.format;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageFormat {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        private int page;
        private int size;

        public Request(final int page, final int size) {
            this.page = page;
            this.size = size;
        }

        public Pageable of() {
            return PageRequest.of(page - 1, size);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response<T> {
        private int page;
        private boolean hasNext;
        private T content;

        public Response(int page, boolean hasNext, T content) {
            this.page = page;
            this.hasNext = hasNext;
            this.content = content;
        }

        public static <T> Response<T> of(final int page, final boolean hasNext, final T content) {
            return new Response<>(page + 1, hasNext, content);
        }
    }

}
