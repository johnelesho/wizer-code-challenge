package tech.elsoft.wizercodechallenge.mapper;

import org.mapstruct.Mapper;
import tech.elsoft.wizercodechallenge.DTOs.requests.CreateBook;
import tech.elsoft.wizercodechallenge.DTOs.requests.CreateBookCategory;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookCategoryResponse;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookResponse;
import tech.elsoft.wizercodechallenge.entities.Book;
import tech.elsoft.wizercodechallenge.entities.BookCategory;

@Mapper(componentModel = "spring")
public interface BookCategoryMapper extends EntityMapper<CreateBookCategory, BookCategoryResponse, BookCategory> {
}