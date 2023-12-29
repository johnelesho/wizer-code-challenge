package tech.elsoft.wizercodechallenge.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
public record ApiResponse (
        LocalDateTime timestamp,
        Object data,
        boolean isSuccess,
        boolean isError,
        String description

) {
    public ApiResponse(Object data){
        this(LocalDateTime.now(), data, true, false, "Operation Completed Successfully");
    }

    public ApiResponse(  Object data, String description){
        this(LocalDateTime.now(), data, false, true, description);
    }
    public ApiResponse(   String description){
        this(LocalDateTime.now(), null, false, true, description);
    }
}
