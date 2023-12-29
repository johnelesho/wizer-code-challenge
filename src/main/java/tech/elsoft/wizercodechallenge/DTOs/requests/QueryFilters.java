package tech.elsoft.wizercodechallenge.DTOs.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryFilters {
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
    private String search;
}

