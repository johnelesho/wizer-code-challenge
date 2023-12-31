package tech.elsoft.wizercodechallenge.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.elsoft.wizercodechallenge.DTOs.requests.CreateBook;
import tech.elsoft.wizercodechallenge.DTOs.responses.BookResponse;
import tech.elsoft.wizercodechallenge.entities.Book;

@Mapper(componentModel = "spring")
public interface BookMapper extends EntityMapper<CreateBook, BookResponse, Book> {
    @Override
    @Mapping(ignore = true, target = "categories")
    Book toEntity(CreateBook dto);
}