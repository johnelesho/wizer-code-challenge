package tech.elsoft.wizercodechallenge.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.elsoft.wizercodechallenge.entities.Book;
import tech.elsoft.wizercodechallenge.entities.BookCategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long>, JpaSpecificationExecutor<BookCategory> {
    List<BookCategory> findAllByCategoryNameIsInIgnoreCase(List<String> namesToCheck);

   Optional<BookCategory> findByCategoryNameEqualsIgnoreCase(String s);

   @Query(value = "select bc.* from api_book_category_tbl bc where bc.deleted= true ", nativeQuery = true)
    Page<BookCategory> findAllDeleted(Pageable pageable);
}
