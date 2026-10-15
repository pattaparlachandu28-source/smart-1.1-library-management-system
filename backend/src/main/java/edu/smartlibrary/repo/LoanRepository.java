package edu.smartlibrary.repo;
import edu.smartlibrary.model.*; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.time.LocalDate; import java.util.*;
public interface LoanRepository extends JpaRepository<Loan,Long>{
 @Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE) @Query("select l from Loan l where l.id=:id") Optional<Loan> lockById(@Param("id") Long id);
 boolean existsByBook_Id(Long bookId);
 long countByStatus(String status); long countByStatusAndDueDateBefore(String status,LocalDate date); long countByBorrowerAndStatus(AppUser borrower,String status);
 List<Loan> findByBorrowerOrderByIssueDateDesc(AppUser borrower); List<Loan> findAllByOrderByIssueDateDesc();
 @Query("select l.book.category,count(l) from Loan l where l.borrower=:user group by l.book.category order by count(l) desc") List<Object[]> preferredCategories(@Param("user") AppUser user);
 @Query("select l.book.id,count(l) from Loan l group by l.book.id order by count(l) desc") List<Object[]> popularBookIds();
 @Query("select l.book.id,count(l) from Loan l where l.book.id in :ids group by l.book.id") List<Object[]> borrowCounts(@Param("ids") Collection<Long> ids);
 @Query("select count(l) from Loan l where l.borrower=:user and l.book.id=:bookId") long countUserBook(@Param("user") AppUser user,@Param("bookId") Long bookId);
 @Query("select count(l) from Loan l where l.borrower=:user and l.book.id=:bookId and l.status='ISSUED'") long countActiveForBook(@Param("user") AppUser user,@Param("bookId") Long bookId);
}
