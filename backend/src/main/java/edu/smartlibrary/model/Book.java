package edu.smartlibrary.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="books",uniqueConstraints=@UniqueConstraint(columnNames="isbn"))
public class Book {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=180) private String title;
    @Column(nullable=false,length=140) private String author;
    @Column(nullable=false,length=80) private String category;
    @Column(length=32) private String isbn;
    @Column(length=140) private String publisher;
    private Integer publicationYear;
    @Column(length=2000) private String description;
    @Column(nullable=false) private Integer totalCopies;
    @Column(nullable=false) private Integer availableCopies;
    @Column(length=80) private String shelfLocation;
    @Version private Long version;
    @Column(nullable=false,updatable=false) private LocalDateTime createdAt=LocalDateTime.now();
    @Column(nullable=false) private LocalDateTime updatedAt=LocalDateTime.now();
    protected Book() {}
    public Book(String title,String author,String category,String isbn,String publisher,Integer year,String description,Integer copies,String shelf){this.title=title;this.author=author;this.category=category;this.isbn=isbn;this.publisher=publisher;this.publicationYear=year;this.description=description;this.totalCopies=copies;this.availableCopies=copies;this.shelfLocation=shelf;}
    @PreUpdate void touch(){updatedAt=LocalDateTime.now();}
    public Long getId(){return id;} public String getTitle(){return title;} public String getAuthor(){return author;} public String getCategory(){return category;} public String getIsbn(){return isbn;} public String getPublisher(){return publisher;} public Integer getPublicationYear(){return publicationYear;} public String getDescription(){return description;} public Integer getTotalCopies(){return totalCopies;} public Integer getAvailableCopies(){return availableCopies;} public String getShelfLocation(){return shelfLocation;} public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
    public void update(String title,String author,String category,String isbn,String publisher,Integer year,String description,Integer copies,String shelf){int onLoan=totalCopies-availableCopies;if(copies<onLoan)throw new IllegalArgumentException("Total copies cannot be less than copies currently borrowed ("+onLoan+").");this.title=title;this.author=author;this.category=category;this.isbn=isbn;this.publisher=publisher;this.publicationYear=year;this.description=description;this.totalCopies=copies;this.availableCopies=copies-onLoan;this.shelfLocation=shelf;}
    public void issueOne(){if(availableCopies<=0)throw new IllegalStateException("No copies are currently available.");availableCopies--;}
    public void returnOne(){if(availableCopies>=totalCopies)throw new IllegalStateException("Inventory is already at its total copy count.");availableCopies++;}
}
