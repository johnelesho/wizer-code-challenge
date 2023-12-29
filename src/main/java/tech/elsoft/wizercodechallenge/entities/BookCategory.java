package tech.elsoft.wizercodechallenge.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "api_book_category_tbl")
public class BookCategory  extends ApiBaseModel{

    @NotBlank()
    @Column(unique = true, nullable = false)
    String categoryName;
    @NotBlank
    @Column( nullable = false)
    String categoryDescription;
}
