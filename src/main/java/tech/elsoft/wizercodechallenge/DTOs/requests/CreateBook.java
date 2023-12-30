package tech.elsoft.wizercodechallenge.DTOs.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public record CreateBook(
        @NotBlank
        String title,

        Set<Long> categories,
        @NotBlank

         String author,

                @NotBlank

 String isbn,

@NotBlank

 String publisher,


 Boolean favourite,
        @PastOrPresent
        LocalDate datePublished
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateBook that)) return false;
        return StringUtils.equalsIgnoreCase(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
