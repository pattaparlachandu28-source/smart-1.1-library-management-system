package edu.smartlibrary.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name="loans",indexes={@Index(columnList="status"),@Index(columnList="dueDate")})
public class Loan {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(nullable=false) private AppUser borrower;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(nullable=false) private Book book;
    @Column(nullable=false) private LocalDate issueDate;
    @Column(nullable=false) private LocalDate dueDate;
    private LocalDate returnDate;
    @Column(nullable=false,precision=10,scale=2) private BigDecimal fine=BigDecimal.ZERO;
    @Column(nullable=false,length=12) private String status="ISSUED";
    protected Loan() {}
    public Loan(AppUser borrower,Book book,LocalDate issueDate,LocalDate dueDate){this.borrower=borrower;this.book=book;this.issueDate=issueDate;this.dueDate=dueDate;}
    public void close(LocalDate date,BigDecimal fine){if(!"ISSUED".equals(status))throw new IllegalStateException("This loan has already been returned.");this.returnDate=date;this.fine=fine;this.status="RETURNED";}
    public Long getId(){return id;} public AppUser getBorrower(){return borrower;} public Book getBook(){return book;} public LocalDate getIssueDate(){return issueDate;} public LocalDate getDueDate(){return dueDate;} public LocalDate getReturnDate(){return returnDate;} public BigDecimal getFine(){return fine;} public String getStatus(){return status;}
}
