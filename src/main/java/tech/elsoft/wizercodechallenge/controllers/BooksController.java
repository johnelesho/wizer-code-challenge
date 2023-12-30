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
import tech.elsoft.wizercodechallenge.DTOs.requests.BookQueryFilter;
import tech.elsoft.wizercodechallenge.DTOs.requests.CreateBook;
import tech.elsoft.wizercodechallenge.DTOs.requests.LongWrapper;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookResponse;
import tech.elsoft.wizercodechallenge.services.interfaces.BookService;

import java.util.List;

@RequestMapping("api/v1/books")
@RestController
@RequiredArgsConstructor
@Slf4j
public class BooksController implements BaseApiController<CreateBook, BookQueryFilter> {

    final BookService bookService;


    @Override
    @PostMapping()
    public ResponseEntity<ApiResponse> addBulk(@Valid @RequestBody List<CreateBook> booksRequest, @ParameterObject @RequestParam(defaultValue = "false") boolean ignoreDuplicate){
        log.debug("Add Bulk Book Controller");
       List<BookResponse> response = bookService.createBooks(booksRequest, ignoreDuplicate);
        if(response.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("No new records were added to the database",response), HttpStatus.OK);
        }
        else if(response.size() != booksRequest.size()){
            return new ResponseEntity<>(new ApiResponse(response.size() + " new records were added to the database out of " + booksRequest.size() + " records sent, others ignored either due to duplicates or their categoryNames already exist in the database",response), HttpStatus.CREATED);

        }
        return new ResponseEntity<>(new ApiResponse(response), HttpStatus.CREATED);
    }

    @PutMapping("favourite-books")
    public ResponseEntity<ApiResponse> addToFavouriteBooks(@Valid @RequestBody LongWrapper bookIds){
        log.debug("Add Favorite Book Controller");
        bookService.addToFavouriteBooks(bookIds.getIds());
       return new ResponseEntity<>(new ApiResponse(), HttpStatus.CREATED);
    }

    @GetMapping("favourite-books")
    public ResponseEntity<ApiResponse> getFavouriteBooks(@ParameterObject Pageable pageable){
        log.debug("Get Favorite Book Controller");
        Page<BookResponse> responses = bookService.getFavouriteBooks(pageable);
       return new ResponseEntity<>(new ApiResponse(responses), HttpStatus.OK);
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
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id) {
         bookService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(), HttpStatus.NO_CONTENT);
    }

    @Override
       @GetMapping()
    public ResponseEntity<ApiResponse> GetAllWithFiltersAndPagination(@ParameterObject Pageable pageable, @ParameterObject BookQueryFilter filters){
        log.debug("Get all Book Controller");
       Page<BookResponse> response = bookService.GetAll(pageable, filters);
       return new ResponseEntity<>(new ApiResponse(response), HttpStatus.OK);
    }
  @Override
       @GetMapping("deleted")
    public ResponseEntity<ApiResponse> GetAllDeleted(@ParameterObject Pageable pageable){
        log.debug("Get all Deleted Book Controller");
       Page<BookResponse> response = bookService.viewAllDeletedBooks(pageable);
       return new ResponseEntity<>(new ApiResponse(response), HttpStatus.OK);
    }


}
