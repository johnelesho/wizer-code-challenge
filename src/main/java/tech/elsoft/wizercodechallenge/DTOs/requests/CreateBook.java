package tech.elsoft.wizercodechallenge.DTOs.requests;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record CreateBook(
        @NotBlank
        String title,
        @NotBlank
        String category,
        @NotEmpty
                @NotNull
        List<String> tags,

        @PastOrPresent
        LocalDate datePublished
) {
}
