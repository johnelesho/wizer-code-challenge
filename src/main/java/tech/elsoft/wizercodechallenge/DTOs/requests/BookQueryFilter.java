package tech.elsoft.wizercodechallenge.DTOs.requests;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookQueryFilter extends QueryFilters{
    private String title;
   
    private String category;
    
    @PastOrPresent
    private LocalDate datePublishedFrom;
    @PastOrPresent
    private LocalDate datePublishedTo;
}
