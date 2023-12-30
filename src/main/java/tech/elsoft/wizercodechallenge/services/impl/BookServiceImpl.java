package tech.elsoft.wizercodechallenge.services.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.elsoft.wizercodechallenge.DTOs.requests.BookQueryFilter;
import tech.elsoft.wizercodechallenge.DTOs.requests.CreateBook;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookResponse;
import tech.elsoft.wizercodechallenge.entities.Book;
import tech.elsoft.wizercodechallenge.entities.BookCategory;
import tech.elsoft.wizercodechallenge.exceptions.ApiBadRequestException;
import tech.elsoft.wizercodechallenge.exceptions.ApiNotFoundException;
import tech.elsoft.wizercodechallenge.mapper.BookMapper;
import tech.elsoft.wizercodechallenge.repositories.BookCategoryRepository;
import tech.elsoft.wizercodechallenge.repositories.BookRepository;
import tech.elsoft.wizercodechallenge.repositories.specifications.FilterSpecification;
import tech.elsoft.wizercodechallenge.services.interfaces.BookService;

import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    final BookRepository bookRepository;
    final BookCategoryRepository bookCategoryRepository;


    final BookMapper bookMapper;


    final EntityManager entityManager;
    @Override
    public List<BookResponse> createBooks(List<CreateBook> bookRequest, boolean ignoreDuplicate) {
        Set<CreateBook> createBookSet = new HashSet<>(bookRequest);

        int difference = Math.abs(createBookSet.size() - bookRequest.size());

        if (!ignoreDuplicate && createBookSet.size() != bookRequest.size()){
            String responseMessage = String.format("ignoreDuplicate is set to false and there about %d duplicate title in the request, set it to true or manually remove duplicate", difference);
            throw new ApiBadRequestException(responseMessage);
        }
        List<String> titlesToCheck = bookRequest.stream().map(CreateBook::title).toList();
        List<Book> foundBasedOnTitle = bookRepository.findAllByTitleIsInIgnoreCase(titlesToCheck);
        List<String> foundTitle = foundBasedOnTitle.stream().map(Book::getTitle).toList();
        createBookSet.removeIf(x-> foundTitle.stream().anyMatch(d -> StringUtils.equalsIgnoreCase(x.title(),d)));
        List<Book> booksToSave = bookMapper.toEntity(createBookSet.stream().toList());

        for(Book bk : booksToSave){
            Set<BookCategory> categories = new HashSet<>();
            CreateBook createBook = createBookSet.stream().filter(x -> StringUtils.equalsIgnoreCase(x.title(), bk.getTitle())).findFirst().orElse(null);
            if(createBook == null || createBook.categories() == null || createBook.categories().isEmpty()) continue;
            categories = new HashSet<>(bookCategoryRepository.findAllById(createBook.categories()));
            bk.setCategories(categories);
        }


        log.info("Saving  {} books out of {} request", booksToSave.size(), bookRequest.size());
        booksToSave = bookRepository.saveAll(booksToSave);
        return mapBookResponseList(booksToSave);
    }

    private  List<BookResponse> mapBookResponseList(List<Book> books) {
       return bookMapper.toResponse(books);

    }

    @Override
    public BookResponse updateDetailsById(Long id, CreateBook bookRequest) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ApiNotFoundException("Book not found with Id " + id));
        Optional<Book> checkNewTitle = bookRepository.findByTitleEqualsIgnoreCase(bookRequest.title());
        if(checkNewTitle.isPresent() && !Objects.equals(book, checkNewTitle.get())){
            throw new ApiBadRequestException("The new title already exist");
        }
        book.setTitle(book.getTitle());
        book.setDatePublished(bookRequest.datePublished());
//        book.setCategory(bookRequest.category());
        book = bookRepository.save(book);

        return mapSingleBookResponse(book);
    }

    @Override
    public BookResponse GetById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ApiNotFoundException("Book not found with Id " + id));

        return mapSingleBookResponse(book);
    }

    private  BookResponse mapSingleBookResponse(Book book) {
       return bookMapper.toResponse(book);

    }

    @Override
    public Page<BookResponse> GetAll(Pageable pageable, BookQueryFilter filters) {
        Specification<Book> specification = FilterSpecification.FindByFilter(filters);
        return bookRepository.findAll(specification, pageable).map(bookMapper::toResponse);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void addToFavouriteBooks(List<Long> booksId) {
        List<Book> books = bookRepository.findAllById(booksId);
        books.forEach(x-> x.setFavourite(Boolean.TRUE));
        bookRepository.saveAll(books);
    }

    @Override
    public Page<BookResponse> getFavouriteBooks(Pageable pageable) {
        Page<Book> books = bookRepository.findAllByFavouriteIsTrue(pageable);
        return books.map(bookMapper::toResponse);
    }
    @Override
    public Page<BookResponse> viewAllDeletedBooks(Pageable pageable) {
        Page<Book> books = bookRepository.findAllByDeletedEquals(pageable);
        return books.map(bookMapper::toResponse);
    }
}
