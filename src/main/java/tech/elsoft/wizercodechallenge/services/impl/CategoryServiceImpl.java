package tech.elsoft.wizercodechallenge.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.elsoft.wizercodechallenge.DTOs.requests.CategoryQueryFilter;
import tech.elsoft.wizercodechallenge.DTOs.requests.CreateBookCategory;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookCategoryResponse;
import tech.elsoft.wizercodechallenge.entities.BookCategory;
import tech.elsoft.wizercodechallenge.exceptions.ApiNotFoundException;
import tech.elsoft.wizercodechallenge.mapper.BookCategoryMapper;
import tech.elsoft.wizercodechallenge.repositories.BookCategoryRepository;
import tech.elsoft.wizercodechallenge.repositories.specifications.ExampleSpecification;
import tech.elsoft.wizercodechallenge.repositories.specifications.RangeSpecification;
import tech.elsoft.wizercodechallenge.repositories.specifications.ZonedDateTimeRange;
import tech.elsoft.wizercodechallenge.services.interfaces.CategoryService;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    final BookCategoryRepository categoryRepository;
    final BookCategoryMapper bookCategoryMapper;
    @Override
    public List<BookCategoryResponse> createCategory(List<CreateBookCategory> request) {
        List<BookCategory> categories = bookCategoryMapper.toEntity(request);
        BeanUtils.copyProperties(request, categories);
        categories = categoryRepository.saveAll(categories);
        return mapBookCategoryResponseList(categories);
    }
    private  List<BookCategoryResponse> mapBookCategoryResponseList(List<BookCategory> categories) {
        return bookCategoryMapper.toResponse(categories);
    }

    @Override
    public BookCategoryResponse updateDetailsById(Long id, CreateBookCategory request) {
        return null;
    }

    @Override
    public BookCategoryResponse GetById(Long id) {
        BookCategory category = categoryRepository.findById(id).orElseThrow(() -> new ApiNotFoundException("BookCategory not found with Id " + id));

        return mapSingleBookCategoryResponse(category);
    }

    private  BookCategoryResponse mapSingleBookCategoryResponse(BookCategory category) {
        return bookCategoryMapper.toResponse(category);
    }
    @Override
    public Page<BookCategoryResponse> GetAll(Pageable pageable, CategoryQueryFilter filters) {
        ExampleMatcher ignoreCase = ExampleMatcher.matchingAny().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        BookCategory bc = new BookCategory();
        bc.setCategoryName(filters.getCategoryName());
        bc.setCategoryDescription(filters.getCategoryDescription());

        Specification<BookCategory> specification = new ExampleSpecification<BookCategory>().getSpecificationFromExample(Example.of(bc, ignoreCase));

        if (filters.getCreatedDateFrom() != null && filters.getCreatedDateTo() != null) {
            ZonedDateTime fromDateTime = ZonedDateTime.of(filters.getCreatedDateFrom(), LocalTime.MIN, ZoneOffset.UTC);
            ZonedDateTime toDateTime = ZonedDateTime.of(filters.getCreatedDateTo(), LocalTime.MAX, ZoneOffset.UTC);

            ZonedDateTimeRange range = new ZonedDateTimeRange("dateCreated", fromDateTime, toDateTime);
            specification = specification.and(new RangeSpecification<BookCategory>().withInRange(range));
        }
        return categoryRepository.findAll(specification, pageable).map(bookCategoryMapper::toResponse);
//        return bookCategoryMapper.toResponse(bcs);
    }
}
