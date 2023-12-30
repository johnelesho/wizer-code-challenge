package tech.elsoft.wizercodechallenge.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "api_book_tbl")
public class Book extends ApiBaseModel {


    @NotBlank
    @Column(unique = true, nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String author;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String isbn;

    @NotBlank
    @Column(nullable = false)
    private String publisher;

    @Column(columnDefinition = "boolean default false")
    private Boolean favourite = Boolean.FALSE;

    @ManyToMany
    private Set<BookCategory> categories = new HashSet<>();

    @PastOrPresent
    @Column(nullable = false)
    private LocalDate datePublished;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        if (!super.equals(o)) return false;
        return StringUtils.equalsIgnoreCase(getTitle(), book.getTitle()) || Objects.equals(getId(), book.getId()) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTitle());
    }
}
