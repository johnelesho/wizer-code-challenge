package tech.elsoft.wizercodechallenge.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.elsoft.wizercodechallenge.DTOs.requests.CreateBook;
import tech.elsoft.wizercodechallenge.DTOs.requests.CreateBookCategory;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookCategoryResponse;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookResponse;
import tech.elsoft.wizercodechallenge.entities.Book;
import tech.elsoft.wizercodechallenge.entities.BookCategory;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface BookCategoryMapper extends EntityMapper<CreateBookCategory, BookCategoryResponse, BookCategory> {
    @Override
    @Mapping(ignore = true, target = "books")
    BookCategory toEntity(CreateBookCategory dto);

//    @Mapping(ignore = true, target = "categories")
//    BookCategoryResponse toResponse(BookCategory entity);


}