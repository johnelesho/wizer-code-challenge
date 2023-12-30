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
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
   List<Book>  findAllByTitleIsInIgnoreCase(List<String> titles);

   Page<Book> findAllByFavouriteIsTrue(Pageable pageable);

   Optional<Book> findByTitleEqualsIgnoreCase(String title);

   @Query(value = "select b.* from api_book_tbl b where b.deleted=true", nativeQuery = true)
   Page<Book> findAllByDeletedEquals(Pageable pageable);
}
