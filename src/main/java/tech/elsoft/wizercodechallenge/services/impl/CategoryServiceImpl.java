package tech.elsoft.wizercodechallenge.services.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.elsoft.wizercodechallenge.DTOs.requests.CategoryQueryFilter;
import tech.elsoft.wizercodechallenge.DTOs.requests.CreateBookCategory;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookCategoryResponse;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookResponse;
import tech.elsoft.wizercodechallenge.entities.Book;
import tech.elsoft.wizercodechallenge.entities.BookCategory;
import tech.elsoft.wizercodechallenge.exceptions.ApiBadRequestException;
import tech.elsoft.wizercodechallenge.exceptions.ApiNotFoundException;
import tech.elsoft.wizercodechallenge.mapper.BookCategoryMapper;
import tech.elsoft.wizercodechallenge.mapper.BookMapper;
import tech.elsoft.wizercodechallenge.repositories.BookCategoryRepository;
import tech.elsoft.wizercodechallenge.repositories.BookRepository;
import tech.elsoft.wizercodechallenge.repositories.specifications.FilterSpecification;
import tech.elsoft.wizercodechallenge.services.interfaces.CategoryService;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    final BookCategoryRepository categoryRepository;
    final BookRepository bookRepository;
    final BookCategoryMapper bookCategoryMapper;
    final BookMapper bookMapper;
    final EntityManager entityManager;
    @Override
    public List<BookCategoryResponse> createCategory(List<CreateBookCategory> request, boolean ignoreDuplicate) {


        // Check the request for duplicates,
        // would have used a set in the payload, but the user needs to know about the duplicates too
        // Duplicate titles should not come from the user
        Set<CreateBookCategory> requestSet = new HashSet<>(request);
        int difference = Math.abs(requestSet.size() - request.size());
        if (!ignoreDuplicate && requestSet.size() != request.size()){

            String responseMessage = String.format("ignoreDuplicate is set to false and there about %d duplicate categoryNames in the request, set it to true or manually remove duplicate", difference);
            throw new ApiBadRequestException(responseMessage);
        }
        log.info("Ignoring {} duplicates catagoryName", difference);
        // Extract the names to check if they exist in the database
        List<String> namesToCheck = requestSet.stream().map(CreateBookCategory::categoryName).toList();
        List<BookCategory> foundBasedOnName = categoryRepository.findAllByCategoryNameIsInIgnoreCase(namesToCheck);
        List<String> foundName = foundBasedOnName.stream().map(BookCategory::getCategoryName).toList();
        Set<BookCategory> categoryEntityFromRequest = bookCategoryMapper.toEntity(requestSet);

        Set<BookCategory> categoriesToSave = new HashSet<>();

        for(BookCategory bk : categoryEntityFromRequest){
            if(foundName.stream().noneMatch(d -> StringUtils.equalsIgnoreCase(bk.getCategoryName(),d))){
                categoriesToSave.add(bk);
            }
        }
        log.info("Saving  {} books out of {} request", categoriesToSave.size(), request.size());
        var response = categoryRepository.saveAll(categoriesToSave);
        return mapBookCategoryResponseList(response);

    }
    private  List<BookCategoryResponse> mapBookCategoryResponseList(List<BookCategory> categories) {
        return bookCategoryMapper.toResponse(categories);
    }

    @Override
    public BookCategoryResponse updateDetailsById(Long id, CreateBookCategory request) {
        BookCategory category = categoryRepository.findById(id).orElseThrow(() -> new ApiNotFoundException("BookCategory not found with Id " + id));
        Optional<BookCategory> checkNewName = categoryRepository.findByCategoryNameEqualsIgnoreCase(request.categoryName());
        if(checkNewName.isPresent() && !Objects.equals(category, checkNewName.get())){
            throw new ApiBadRequestException("The new categoryName already exist");
        }
        category.setCategoryDescription(request.categoryDescription());
        category.setCategoryName(request.categoryName());

        category = categoryRepository.save(category);
        return mapSingleBookCategoryResponse(category);
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

        Specification<BookCategory> bookCategorySpecification = FilterSpecification.FindByFilter(filters);

        return categoryRepository.findAll(bookCategorySpecification, pageable).map(bookCategoryMapper::toResponse);
    }

    @Override
    public void addBooksToCategory(Long id, List<Long> bookIds) {
        BookCategory category = categoryRepository.findById(id).orElseThrow(() -> new ApiNotFoundException("BookCategory not found with Id " + id));
        List<Book> allBooks = bookRepository.findAllById(bookIds);
        category.setBooks(new HashSet<>(allBooks));
        BookCategory save = categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Page<BookResponse> getBooksInACategory(Long id, Pageable pageable) {
        BookCategory category = categoryRepository.findById(id).orElseThrow(() -> new ApiNotFoundException("BookCategory not found with Id " + id));
        if(category.getBooks() == null || category.getBooks().isEmpty()){
            throw new ApiNotFoundException("There are no books in the category");
        }
       return new PageImpl<>(bookMapper.toResponse(category.getBooks()).stream().toList(), pageable, category.getBooks().size());
    }
    @Override
    public Page<BookCategoryResponse> viewAllDeletedCategory(Pageable pageable) {
        Page<BookCategory> books = categoryRepository.findAllDeleted(pageable);
        return books.map(bookCategoryMapper::toResponse);
    }
}
