package tech.elsoft.wizercodechallenge.DTOs.responses;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


public record BookCategoryResponse (
      Long id,


      String categoryName,

      String categoryDescription,
        LocalDate dateCreated,

        LocalDate dateModified
) {
}
