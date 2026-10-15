package edu.smartlibrary.repo;
import edu.smartlibrary.model.Book; import jakarta.persistence.LockModeType; import org.springframework.data.domain.*; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface BookRepository extends JpaRepository<Book,Long>{
 boolean existsByIsbnIgnoreCase(String isbn);
 boolean existsByIsbnIgnoreCaseAndIdNot(String isbn,Long id);
 boolean existsByTitleIgnoreCaseAndAuthorIgnoreCase(String title,String author);
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select b from Book b where b.id=:id") Optional<Book> lockById(@Param("id") Long id);
 @Query("select b from Book b where (:term is null or lower(b.title) like lower(concat('%',:term,'%')) or lower(b.author) like lower(concat('%',:term,'%')) or lower(b.category) like lower(concat('%',:term,'%')) or lower(coalesce(b.isbn,'')) like lower(concat('%',:term,'%'))) and (:category is null or lower(b.category)=lower(:category))") Page<Book> search(@Param("term") String term,@Param("category") String category,Pageable pageable);
 @Query("select b.category,count(b) from Book b group by b.category order by count(b) desc") List<Object[]> categoryStats();
}
