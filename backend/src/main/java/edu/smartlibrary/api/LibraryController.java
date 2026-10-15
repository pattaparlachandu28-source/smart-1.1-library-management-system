package edu.smartlibrary.api;
import edu.smartlibrary.api.ApiModels.*;import edu.smartlibrary.model.*;import edu.smartlibrary.repo.UserRepository;import edu.smartlibrary.security.CurrentUser;import edu.smartlibrary.service.LibraryService;import jakarta.servlet.http.HttpServletRequest;import jakarta.validation.Valid;import jakarta.validation.constraints.*;import org.springframework.data.domain.Page;import org.springframework.http.*;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api") public class LibraryController{
 private final LibraryService service;private final CurrentUser current;private final UserRepository users;private final PasswordEncoder passwords;
 public LibraryController(LibraryService s,CurrentUser c,UserRepository u,PasswordEncoder p){service=s;current=c;users=u;passwords=p;}
 @GetMapping("/books") public Page<BookView> books(@RequestParam(required=false) String q,@RequestParam(required=false) String category,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="24") int size,HttpServletRequest req){current.get(req);return service.searchBooks(q,category,page,size);}
 @PostMapping("/books") public ResponseEntity<BookView> createBook(@Valid @RequestBody BookRequest r,HttpServletRequest req){current.librarian(req);return ResponseEntity.status(HttpStatus.CREATED).body(service.createBook(r));}
 @PutMapping("/books/{id}") public BookView updateBook(@PathVariable Long id,@Valid @RequestBody BookRequest r,HttpServletRequest req){current.librarian(req);return service.updateBook(id,r);}
 @DeleteMapping("/books/{id}") public ResponseEntity<Void> deleteBook(@PathVariable Long id,HttpServletRequest req){current.librarian(req);service.deleteBook(id);return ResponseEntity.noContent().build();}
 @GetMapping("/dashboard") public Map<String,Object> dashboard(HttpServletRequest req){AppUser u=current.get(req);return service.dashboard(u,u.getRole()==Role.LIBRARIAN);}
 @GetMapping("/library-rules") public Map<String,Object> libraryRules(HttpServletRequest req){current.get(req);return service.libraryRules();}
 @GetMapping("/transactions") public List<LoanView> transactions(HttpServletRequest req){AppUser u=current.get(req);return service.transactions(u,u.getRole()==Role.LIBRARIAN);}
 @PostMapping("/transactions/issue") public ResponseEntity<LoanView> issue(@Valid @RequestBody LoanRequest r,HttpServletRequest req){AppUser u=current.get(req);return ResponseEntity.status(HttpStatus.CREATED).body(service.issue(u,u.getRole()==Role.LIBRARIAN,r));}
 @PostMapping("/transactions/{id}/return") public LoanView returnBook(@PathVariable Long id,HttpServletRequest req){AppUser u=current.get(req);return service.returnLoan(u,u.getRole()==Role.LIBRARIAN,id);}
 @GetMapping("/recommendations") public List<Recommendation> recommendations(HttpServletRequest req){AppUser u=current.get(req);if(u.getRole()!=Role.STUDENT)throw new org.springframework.web.server.ResponseStatusException(HttpStatus.FORBIDDEN,"Recommendations are available to student accounts.");return service.recommendations(u);}
 @GetMapping("/members") public List<Map<String,Object>> members(HttpServletRequest req){current.librarian(req);return service.members();}
 public record MemberRequest(@NotBlank @Size(max=100) String name,@Email @NotBlank String email,@NotBlank @Size(min=8,max=100) String password){}
 @PostMapping("/members") public ResponseEntity<UserView> createMember(@Valid @RequestBody MemberRequest r,HttpServletRequest req){current.librarian(req);return ResponseEntity.status(HttpStatus.CREATED).body(service.createMember(r.name(),r.email(),r.password(),passwords));}
}
