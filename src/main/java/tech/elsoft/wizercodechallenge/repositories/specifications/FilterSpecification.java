package tech.elsoft.wizercodechallenge.repositories.specifications;

import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import tech.elsoft.wizercodechallenge.DTOs.requests.BookQueryFilter;
import tech.elsoft.wizercodechallenge.DTOs.requests.CategoryQueryFilter;
import tech.elsoft.wizercodechallenge.entities.Book;
import tech.elsoft.wizercodechallenge.entities.BookCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FilterSpecification {

public static Specification<BookCategory> FindByFilter(CategoryQueryFilter filter){
    return (root, query, cb) -> {
        List<Predicate> predicates = new ArrayList<>();
        if(StringUtils.isNotBlank(filter.getSearch())){
            String search = "%" + StringUtils.lowerCase(filter.getSearch()) + "%";
           Predicate searchCategory = cb.like(cb.lower(root.get("categoryName")), search);
           Predicate searchDescription = cb.like(cb.lower(root.get("categoryDescription")),search);
            predicates.add(cb.or(searchCategory,searchDescription));
        }

        if(StringUtils.isNotBlank(filter.getCategoryName())) {
            Predicate categoryName = (cb.like(cb.lower(root.get("categoryName")), "%"+ StringUtils.lowerCase(filter.getCategoryName()) + "%"));
            predicates.add(categoryName);
        }
        if(StringUtils.isNotBlank(filter.getCategoryDescription())) {
           Predicate cateDescription = (cb.like(cb.lower(root.get("categoryDescription")), "%"+ StringUtils.lowerCase(filter.getCategoryDescription())
           +"%"));
          predicates.add(cateDescription);
        }
        if(filter.getCreatedDateFrom() != null && filter.getCreatedDateTo() != null){
          predicates.add(cb.between(root.get("dateCreated"), filter.getCreatedDateFrom(), filter.getCreatedDateTo()));
        }
        else if(filter.getCreatedDateFrom() != null){
          predicates.add(cb.between(root.get("dateCreated"), filter.getCreatedDateFrom(), LocalDateTime.now()));
        }
        return cb.and(predicates.toArray(new Predicate[0]));

    };
}
public static Specification<Book> FindByFilter(BookQueryFilter filter){

    return (root, query, cb) -> {
        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.isNotBlank(filter.getSearch())){
            String search = "%" + StringUtils.lowerCase(filter.getSearch()) + "%";
           Predicate searchTitle = cb.like(cb.lower(root.get("title")), search );
           Predicate searchAuthor = cb.like(cb.lower(root.get("author")), search);
           Predicate searchPublisher = cb.like(cb.lower(root.get("publisher")), search);
           Predicate searchIsbn = cb.like(cb.lower(root.get("isbn")), search);
           predicates.add(cb.or(searchTitle,searchAuthor,searchPublisher,searchIsbn));
        }

        if(StringUtils.isNotBlank(filter.getIsbn())) {
            String search = "%" + StringUtils.lowerCase(filter.getIsbn()) + "%";
            Predicate isbn = cb.like(cb.lower(root.get("isbn")), search);
            predicates.add(isbn);

        }
        if(StringUtils.isNotBlank(filter.getAuthor())) {
            String search = "%" + StringUtils.lowerCase(filter.getAuthor()) + "%";
           Predicate author = cb.like(cb.lower(root.get("author")), search);
           predicates.add(author);
        }
        if(StringUtils.isNotBlank(filter.getTitle())) {
            String search = "%" + StringUtils.lowerCase(filter.getTitle()) + "%";
            Predicate title = cb.like(cb.lower(root.get("title")), search);
           predicates.add(title);
        }
        if(StringUtils.isNotBlank(filter.getPublisher())) {
            String search = "%" + StringUtils.lowerCase(filter.getPublisher()) + "%";
            Predicate publisher = cb.like(cb.lower(root.get("publisher")), search);
           cb.and(publisher);
        }
        if(filter.getCreatedDateFrom() != null && filter.getCreatedDateTo() != null){
          predicates.add(cb.between(root.get("dateCreated"), filter.getCreatedDateFrom(), filter.getCreatedDateTo()));
        }
        if(filter.getCreatedDateFrom() != null){
          predicates.add(cb.between(root.get("dateCreated"), filter.getCreatedDateFrom(), LocalDateTime.now()));
        }

        if(filter.getDatePublishedFrom() != null && filter.getDatePublishedTo() != null){
          predicates.add(cb.between(root.get("datePublished"), filter.getDatePublishedFrom(), filter.getDatePublishedTo()));
        }
        if(filter.getDatePublishedFrom() != null){
          predicates.add(cb.between(root.get("datePublished"), filter.getDatePublishedFrom(), LocalDate.now()));
        }

        Predicate favorite = cb.equal(root.get("favourite"), filter.getFavorite());
        predicates.add(favorite);

        return cb.and(predicates.toArray(new Predicate[0]));

    };
}
}
