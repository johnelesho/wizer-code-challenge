package tech.elsoft.wizercodechallenge.DTOs.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryFilters {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)

    private LocalDateTime createdDateFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)

    private LocalDateTime createdDateTo;
    private String search;
}

