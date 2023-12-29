package tech.elsoft.wizercodechallenge.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.elsoft.wizercodechallenge.DTOs.requests.BookQueryFilter;
import tech.elsoft.wizercodechallenge.DTOs.requests.CreateBook;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookResponse;

import java.util.List;

public interface BookService {
    List<BookResponse> createBooks(List<CreateBook> bookRequest);

    BookResponse updateDetailsById(Long id, CreateBook bookRequest);

    BookResponse GetById(Long id);

    Page<BookResponse> GetAll(Pageable pageable, BookQueryFilter filters);
}
