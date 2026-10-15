package edu.smartlibrary.security;
import edu.smartlibrary.model.AppUser; import edu.smartlibrary.repo.UserRepository; import jakarta.servlet.http.*; import org.springframework.http.HttpStatus; import org.springframework.stereotype.Component; import org.springframework.web.server.ResponseStatusException;
@Component public class CurrentUser {
 private final TokenService tokens;private final UserRepository users;public CurrentUser(TokenService t,UserRepository u){tokens=t;users=u;}
 public AppUser get(HttpServletRequest req){String h=req.getHeader("Authorization");if(h==null||!h.startsWith("Bearer "))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Please sign in.");Long id=tokens.userId(h.substring(7));if(id==null)throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Your session is invalid or expired.");return users.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Account not found."));}
 public AppUser librarian(HttpServletRequest req){AppUser u=get(req);if(u.getRole()!=edu.smartlibrary.model.Role.LIBRARIAN)throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Librarian access is required.");return u;}
}
