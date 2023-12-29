package tech.elsoft.wizercodechallenge.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.elsoft.wizercodechallenge.DTOs.ApiResponse;
import tech.elsoft.wizercodechallenge.DTOs.requests.BookQueryFilter;
import tech.elsoft.wizercodechallenge.DTOs.requests.CreateBook;
import tech.elsoft.wizercodechallenge.DTOs.requests.QueryFilters;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookResponse;
import tech.elsoft.wizercodechallenge.services.interfaces.BookService;

import java.util.List;

@RequestMapping("books")
@RestController
@RequiredArgsConstructor
@Slf4j
public class BooksController implements BaseApiController<CreateBook, BookQueryFilter> {

    final BookService bookService;


    @Override
    @PostMapping
    public ResponseEntity<ApiResponse> addOne(@RequestBody @Valid CreateBook bookRequest){
        log.debug("Add Book Controller");
       List<BookResponse> response = bookService.createBooks(List.of(bookRequest));
       return new ResponseEntity<>(new ApiResponse(response), HttpStatus.CREATED);
    }
    @Override
    @PostMapping("bulk-add")
    public ResponseEntity<ApiResponse> addBulk(@Valid @RequestBody List<CreateBook> booksRequest){
        log.debug("Add Bulk Book Controller");
       List<BookResponse> response = bookService.createBooks(booksRequest);
       return new ResponseEntity<>(new ApiResponse(response), HttpStatus.CREATED);
    }
    @Override
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> editById(@PathVariable Long id, @Valid @RequestBody CreateBook bookRequest){
        log.debug("Edit Book Controller");
       BookResponse response = bookService.updateDetailsById(id, bookRequest);
       return new ResponseEntity<>(new ApiResponse(response), HttpStatus.OK);
    }
     @Override
     @GetMapping("{id}")
    public ResponseEntity<ApiResponse> GetById(@PathVariable Long id){
        log.debug("Get Book By Id");
       BookResponse response = bookService.GetById(id);
       return new ResponseEntity<>(new ApiResponse(response), HttpStatus.OK);
    }
       @Override
       @GetMapping()
    public ResponseEntity<ApiResponse> GetAllWithFiltersAndPagination(@ParameterObject Pageable pageable, @ParameterObject BookQueryFilter filters){
        log.debug("Get all Book Controller");
       Page<BookResponse> response = bookService.GetAll(pageable, filters);
       return new ResponseEntity<>(new ApiResponse(response), HttpStatus.OK);
    }


}
