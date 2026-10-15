package edu.smartlibrary.api;

import edu.smartlibrary.model.Role;
import jakarta.validation.constraints.*;
import java.time.*;
import java.math.BigDecimal;

public final class ApiModels {
 private ApiModels(){}
 public record UserView(Long id,String name,String email,String role){}
 public record AuthResponse(String token,UserView user){}
 public record RegisterRequest(@NotBlank @Size(max=100) String name,@Email @NotBlank String email,@NotBlank @Size(min=8,max=100) String password,String setupKey,Role role){}
 public record LoginRequest(@Email @NotBlank String email,@NotBlank String password){}
 public record BookRequest(@NotBlank @Size(max=180) String title,@NotBlank @Size(max=140) String author,@NotBlank @Size(max=80) String category,@Size(max=32) String isbn,@Size(max=140) String publisher,@Min(0) @Max(2200) Integer publicationYear,@Size(max=2000) String description,@NotNull @Min(1) @Max(10000) Integer totalCopies,@Size(max=80) String shelfLocation){}
 public record BookView(Long id,String title,String author,String category,String isbn,String publisher,Integer publicationYear,String description,Integer totalCopies,Integer availableCopies,String shelfLocation,LocalDateTime createdAt,LocalDateTime updatedAt){}
 public record LoanRequest(@NotNull Long bookId,Long studentId){}
 public record LoanView(Long id,Long bookId,String title,String author,Long studentId,String studentName,String studentEmail,LocalDate issueDate,LocalDate dueDate,LocalDate returnDate,BigDecimal fine,String status,long overdueDays,BigDecimal currentFine){}
 public record Recommendation(BookView book,String reason,long borrowCount){}
}
