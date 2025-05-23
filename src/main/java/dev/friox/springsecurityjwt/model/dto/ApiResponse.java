package dev.friox.springsecurityjwt.model.dto;

import dev.friox.springsecurityjwt.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {

    private final boolean success;
    private final T data;
    private final ErrorCode error;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(ErrorCode error) {
        return ApiResponse.<T>builder()
                .success(false)
                .error(error)
                .build();
    }
}
