package tech.elsoft.wizercodechallenge.DTOs.requests;

import jakarta.validation.constraints.NotBlank;

public record CreateBookCategory(

                @NotBlank()
        String categoryName,
        @NotBlank
        String categoryDescription

) {
}
