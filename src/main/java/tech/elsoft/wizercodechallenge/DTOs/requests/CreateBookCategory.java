package tech.elsoft.wizercodechallenge.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public record CreateBookCategory(

                @NotBlank()
        String categoryName,
        @NotBlank
        String categoryDescription

) {
        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof CreateBookCategory that)) return false;
            return StringUtils.equalsIgnoreCase(categoryName, that.categoryName);
        }

        @Override
        public int hashCode() {
                return Objects.hash(categoryName);
        }
}
