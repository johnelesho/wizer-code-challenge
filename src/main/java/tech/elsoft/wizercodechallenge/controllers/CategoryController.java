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
import tech.elsoft.wizercodechallenge.DTOs.requests.LongWrapper;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookCategoryResponse;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookResponse;
import tech.elsoft.wizercodechallenge.services.interfaces.CategoryService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/v1/book-category")
@RestController
@Slf4j
public class CategoryController implements BaseApiController<CreateBookCategory, CategoryQueryFilter> {

    final CategoryService categoryService;


    @Override
    @PostMapping()
    public ResponseEntity<ApiResponse> addBulk(@Valid @RequestBody List<CreateBookCategory> request, @ParameterObject @RequestParam(defaultValue = "false") boolean ignoreDuplicate){
        log.debug("Add Bulk Book Controller");
        List<BookCategoryResponse> response = categoryService.createCategory(request, ignoreDuplicate);
        if(response.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("No new records were added to the database.",response), HttpStatus.OK);
        }
        else if(response.size() != request.size()){
            return new ResponseEntity<>(new ApiResponse(response.size() + " new records were added to the database out of " + request.size() + " records sent, others ignored either due to duplicates or their categoryNames already exist in the database",response), HttpStatus.CREATED);

        }
        return new ResponseEntity<>(new ApiResponse(response), HttpStatus.CREATED);
    }

    @PutMapping("{id}/books")
    public ResponseEntity<ApiResponse> addBooksToCategory(@PathVariable Long id, @Valid @RequestBody LongWrapper bookIds){
        log.debug("Add Books To Category Controller");
        categoryService.addBooksToCategory(id, bookIds.getIds());
        return new ResponseEntity<>(new ApiResponse(), HttpStatus.OK);
    }
    @GetMapping("{id}/books")
    public ResponseEntity<ApiResponse> getBooksInACategory(@PathVariable Long id, @ParameterObject Pageable pageable){
        log.debug("Get Books in Category Controller");
        Page<BookResponse> responses = categoryService.getBooksInACategory(id, pageable);
        return new ResponseEntity<>(new ApiResponse(responses), HttpStatus.OK);
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
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id) {
        log.debug("Delete Book Category By Id");
         categoryService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(), HttpStatus.NO_CONTENT);
    }

    @Override
    @GetMapping()
    public ResponseEntity<ApiResponse> GetAllWithFiltersAndPagination(@ParameterObject Pageable pageable, @ParameterObject CategoryQueryFilter filters){
        log.debug("Get all Book Controller");
        Page<BookCategoryResponse> response = categoryService.GetAll(pageable, filters);
        return new ResponseEntity<>(new ApiResponse(response), HttpStatus.OK);
    }

    @Override
    @GetMapping("deleted")
    public ResponseEntity<ApiResponse> GetAllDeleted(Pageable pageable) {
        log.debug("Get all Deleted Book Controller");
        Page<BookCategoryResponse> response = categoryService.viewAllDeletedCategory(pageable);
        return new ResponseEntity<>(new ApiResponse(response), HttpStatus.OK);
    }
}
