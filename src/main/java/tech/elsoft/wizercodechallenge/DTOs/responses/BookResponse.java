package tech.elsoft.wizercodechallenge.DTOs.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


public record BookResponse (
      Long id,
        String title,
String author,
       Boolean favourite,
        String category,

        LocalDate datePublished,
        LocalDate dateCreated,

        LocalDate dateModified
) {
}
