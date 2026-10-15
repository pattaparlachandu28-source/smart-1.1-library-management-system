package edu.smartlibrary.service;

import edu.smartlibrary.model.Book;
import edu.smartlibrary.repo.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/** Adds missing starter titles without adding fake loans or dashboard activity. */
@Component
public class BookCatalogSeeder implements CommandLineRunner {
    private final BookRepository books;

    public BookCatalogSeeder(BookRepository books) {
        this.books = books;
    }

    @Override
    public void run(String... args) {
        List<Book> starterBooks = List.of(
            book("Clean Code", "Robert C. Martin", "Technology", "Practical guidance for writing readable, maintainable software.", 2008, 4, "T-01"),
            book("The Pragmatic Programmer", "Andrew Hunt and David Thomas", "Technology", "A practical guide to habits and techniques for software craftsmanship.", 1999, 4, "T-02"),
            book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", "Computer Science", "A broad introduction to algorithms, data structures, and computational analysis.", 2022, 3, "CS-01"),
            book("Computer Networking: A Top-Down Approach", "James F. Kurose and Keith W. Ross", "Computer Science", "An application-focused introduction to computer networking concepts.", 2021, 3, "CS-02"),
            book("Database System Concepts", "Abraham Silberschatz, Henry F. Korth and S. Sudarshan", "Computer Science", "Core concepts in relational databases, transactions, and data management.", 2019, 3, "CS-03"),
            book("Operating System Concepts", "Abraham Silberschatz, Peter B. Galvin and Greg Gagne", "Computer Science", "An introduction to operating system design and implementation.", 2018, 3, "CS-04"),
            book("Java: The Complete Reference", "Herbert Schildt", "Programming", "A detailed reference for Java language features and programming.", 2021, 3, "P-01"),
            book("Head First Java", "Kathy Sierra and Bert Bates", "Programming", "A visual, example-driven introduction to Java programming.", 2022, 3, "P-02"),
            book("Eloquent JavaScript", "Marijn Haverbeke", "Programming", "A hands-on guide to JavaScript, the browser, and asynchronous programming.", 2018, 3, "P-03"),
            book("Deep Learning", "Ian Goodfellow, Yoshua Bengio and Aaron Courville", "Artificial Intelligence", "An academic introduction to deep learning methods and their foundations.", 2016, 2, "AI-01"),
            book("The Lean Startup", "Eric Ries", "Business", "A framework for testing ideas and building new products through learning.", 2011, 2, "B-01"),
            book("Atomic Habits", "James Clear", "Personal Development", "A practical approach to building habits through small, repeatable changes.", 2018, 3, "PD-01"),
            book("The Alchemist", "Paulo Coelho", "Fiction", "A philosophical novel about a shepherd's journey in search of a personal legend.", 1988, 3, "F-01"),
            book("To Kill a Mockingbird", "Harper Lee", "Fiction", "A novel about justice, childhood, and community in the American South.", 1960, 2, "F-02"),
            book("1984", "George Orwell", "Fiction", "A dystopian novel exploring surveillance, language, and political power.", 1949, 3, "F-03"),
            book("Pride and Prejudice", "Jane Austen", "Fiction", "A classic novel of manners, family, and first impressions.", 1813, 2, "F-04"),
            book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "A novel of ambition and reinvention set in the Jazz Age.", 1925, 2, "F-05"),
            book("The Hobbit", "J. R. R. Tolkien", "Fantasy", "A fantasy adventure following Bilbo Baggins on an unexpected journey.", 1937, 3, "FA-01"),
            book("Wings of Fire", "A. P. J. Abdul Kalam and Arun Tiwari", "Biography", "The autobiography of scientist and former Indian president A. P. J. Abdul Kalam.", 1999, 4, "BIO-01"),
            book("India After Gandhi", "Ramachandra Guha", "History", "A history of India from independence through its early decades as a republic.", 2007, 2, "H-01"),
            book("Sapiens", "Yuval Noah Harari", "History", "A broad account of human history and the development of societies.", 2011, 3, "H-02"),
            book("The Psychology of Money", "Morgan Housel", "Finance", "Essays on how behavior and personal experience shape financial decisions.", 2020, 3, "FI-01"),
            book("Cosmos", "Carl Sagan", "Science", "A guide to the universe and humanity's place in the history of science.", 1980, 2, "S-01"),
            book("A Brief History of Time", "Stephen Hawking", "Science", "An accessible exploration of cosmology, time, and the universe.", 1988, 2, "S-02")
        );
        List<Book> missingBooks = starterBooks.stream()
            .filter(b -> !books.existsByTitleIgnoreCaseAndAuthorIgnoreCase(b.getTitle(), b.getAuthor()))
            .toList();
        if (!missingBooks.isEmpty()) books.saveAll(missingBooks);
    }

    private Book book(String title, String author, String category, String description,
                      int year, int copies, String shelf) {
        return new Book(title, author, category, null, null, year, description, copies, shelf);
    }
}
