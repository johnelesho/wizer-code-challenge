package tech.elsoft.wizercodechallenge.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "api_book_category_tbl")
public class BookCategory  extends ApiBaseModel{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookCategory category)) return false;
        if (!super.equals(o)) return false;
        return StringUtils.equalsIgnoreCase(getCategoryName(), category.getCategoryName()) || Objects.equals(super.getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCategoryName());
    }

    @NotBlank()
    @Column(unique = true, nullable = false)
  private   String categoryName;
    @NotBlank
    @Column( nullable = false, length = 1000)
  private   String categoryDescription;

    @ManyToMany
    private Set<Book> books = new HashSet<>();
}
