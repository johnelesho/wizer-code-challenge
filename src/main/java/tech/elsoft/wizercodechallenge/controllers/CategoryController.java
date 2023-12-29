package tech.elsoft.wizercodechallenge.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.elsoft.wizercodechallenge.DTOs.ApiResponse;
import tech.elsoft.wizercodechallenge.DTOs.requests.CategoryQueryFilter;
import tech.elsoft.wizercodechallenge.DTOs.requests.CreateBookCategory;
import tech.elsoft.wizercodechallenge.DTOs.requests.QueryFilters;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookCategoryResponse;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookResponse;
import tech.elsoft.wizercodechallenge.services.interfaces.CategoryService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("book-category")
@RestController
@Slf4j
public class CategoryController implements BaseApiController<CreateBookCategory, CategoryQueryFilter> {

    final CategoryService categoryService;

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse> addOne(@RequestBody @Valid CreateBookCategory request){
        log.debug("Add Book Controller");
       List<BookCategoryResponse> response = categoryService.createCategory(List.of(request));
        return new ResponseEntity<>(new ApiResponse(response), HttpStatus.CREATED);
    }
    @Override
    @PostMapping("bulk-add")
    public ResponseEntity<ApiResponse> addBulk(@Valid @RequestBody List<CreateBookCategory> request){
        log.debug("Add Bulk Book Controller");
        List<BookCategoryResponse> response = categoryService.createCategory(request);
        return new ResponseEntity<>(new ApiResponse(response), HttpStatus.CREATED);
    }
    @Override
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> editById(@PathVariable Long id, @Valid @RequestBody CreateBookCategory request){
        log.debug("Edit Book Category Controller");
        BookCategoryResponse response = categoryService.updateDetailsById(id, request);
        return new ResponseEntity<>(new ApiResponse(response), HttpStatus.OK);
    }
    @Override
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> GetById(@PathVariable Long id){
        log.debug("Get Book Category By Id");
        BookCategoryResponse response = categoryService.GetById(id);
        return new ResponseEntity<>(new ApiResponse(response), HttpStatus.OK);
    }
    @Override
    @GetMapping()
    public ResponseEntity<ApiResponse> GetAllWithFiltersAndPagination(@ParameterObject Pageable pageable, @ParameterObject CategoryQueryFilter filters){
        log.debug("Get all Book Controller");
        Page<BookCategoryResponse> response = categoryService.GetAll(pageable, filters);
        return new ResponseEntity<>(new ApiResponse(response), HttpStatus.OK);
    }


}
