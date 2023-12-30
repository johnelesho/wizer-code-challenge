package tech.elsoft.wizercodechallenge.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.elsoft.wizercodechallenge.DTOs.requests.CategoryQueryFilter;
import tech.elsoft.wizercodechallenge.DTOs.requests.CreateBookCategory;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookCategoryResponse;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookResponse;

import java.util.List;

public interface CategoryService {
    List<BookCategoryResponse> createCategory(List<CreateBookCategory> request, boolean ignoreDuplicate);

    BookCategoryResponse updateDetailsById(Long id, CreateBookCategory request);

    BookCategoryResponse GetById(Long id);

    Page<BookCategoryResponse> GetAll(Pageable pageable, CategoryQueryFilter filters);

    void addBooksToCategory(Long id, List<Long> bookIds);

    void deleteById(Long id);

    Page<BookResponse> getBooksInACategory(Long id, Pageable pageable);

    Page<BookCategoryResponse> viewAllDeletedCategory(Pageable pageable);
}
