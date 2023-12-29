package tech.elsoft.wizercodechallenge.controllers;

import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.elsoft.wizercodechallenge.DTOs.ApiResponse;
import tech.elsoft.wizercodechallenge.DTOs.requests.QueryFilters;

import java.util.List;

public interface BaseApiController<R, K extends QueryFilters> {
    @PostMapping
    ResponseEntity<ApiResponse> addOne(@RequestBody @Valid R request);

    @PostMapping("bulk-add")
    ResponseEntity<ApiResponse> addBulk(@Valid @RequestBody List<R> request);

    @PutMapping("{id}")
    ResponseEntity<ApiResponse> editById(@PathVariable Long id, @Valid @RequestBody R request);

    @GetMapping("{id}")
    ResponseEntity<ApiResponse> GetById(@PathVariable Long id);

    @GetMapping()
    ResponseEntity<ApiResponse> GetAllWithFiltersAndPagination(Pageable pageable, @ParameterObject K filters);
}
