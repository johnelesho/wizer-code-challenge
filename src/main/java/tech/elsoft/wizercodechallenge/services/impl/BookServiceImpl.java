package tech.elsoft.wizercodechallenge.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.elsoft.wizercodechallenge.DTOs.requests.BookQueryFilter;
import tech.elsoft.wizercodechallenge.DTOs.requests.CreateBook;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookResponse;
import tech.elsoft.wizercodechallenge.entities.Book;
import tech.elsoft.wizercodechallenge.exceptions.ApiNotFoundException;
import tech.elsoft.wizercodechallenge.mapper.BookMapper;
import tech.elsoft.wizercodechallenge.repositories.BookRepository;
import tech.elsoft.wizercodechallenge.repositories.specifications.ExampleSpecification;
import tech.elsoft.wizercodechallenge.repositories.specifications.RangeSpecification;
import tech.elsoft.wizercodechallenge.repositories.specifications.ZonedDateTimeRange;
import tech.elsoft.wizercodechallenge.services.interfaces.BookService;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    final BookRepository bookRepository;

    final BookMapper bookMapper;
    @Override
    public List<BookResponse> createBooks(List<CreateBook> bookRequest) {
        List<Book> books = bookMapper.toEntity(bookRequest);
        books = bookRepository.saveAll(books);
        return mapBookResponseList(books);
    }

    private  List<BookResponse> mapBookResponseList(List<Book> books) {
       return bookMapper.toResponse(books);

    }

    @Override
    public BookResponse updateDetailsById(Long id, CreateBook bookRequest) {
        return null;
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

        ExampleMatcher ignoreCase = ExampleMatcher.matchingAny().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Book book = new Book();
        book.setCategory(filters.getSearch());
        book.setTitle(filters.getTitle());
        book.setCategory(filters.getCategory());

        Specification<Book> specification = new ExampleSpecification<Book>().getSpecificationFromExample(Example.of(book, ignoreCase));

        if (filters.getDatePublishedFrom() != null && filters.getDatePublishedTo() != null) {
            ZonedDateTime fromDateTime = ZonedDateTime.of(filters.getDatePublishedFrom(), LocalTime.MIN, ZoneOffset.UTC);
            ZonedDateTime toDateTime = ZonedDateTime.of(filters.getDatePublishedTo(), LocalTime.MAX, ZoneOffset.UTC);

            ZonedDateTimeRange range = new ZonedDateTimeRange("datePublished", fromDateTime, toDateTime);
            specification = specification.and(new RangeSpecification<Book>().withInRange(range));
        }
        if (filters.getCreatedDateFrom() != null && filters.getCreatedDateTo() != null) {
            ZonedDateTime fromDateTime = ZonedDateTime.of(filters.getCreatedDateFrom(), LocalTime.MIN, ZoneOffset.UTC);
            ZonedDateTime toDateTime = ZonedDateTime.of(filters.getCreatedDateTo(), LocalTime.MAX, ZoneOffset.UTC);

            ZonedDateTimeRange range = new ZonedDateTimeRange("dateCreated", fromDateTime, toDateTime);
            specification = specification.and(new RangeSpecification<Book>().withInRange(range));
        }
        return bookRepository.findAll(specification, pageable).map(bookMapper::toResponse);
//        return bookMapper.toResponse(books);
    }
}
