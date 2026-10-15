package edu.smartlibrary.security;
import edu.smartlibrary.model.AppUser; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import javax.crypto.Mac; import javax.crypto.spec.SecretKeySpec; import java.nio.charset.StandardCharsets; import java.time.Instant; import java.util.*;
@Service public class TokenService {
 @Value("${app.jwt.secret}") private String secret; @Value("${app.jwt.expiry-hours}") private long expiryHours;
 private String sign(String data){try{Mac mac=Mac.getInstance("HmacSHA256");mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),"HmacSHA256"));return Base64.getUrlEncoder().withoutPadding().encodeToString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));}catch(Exception e){throw new IllegalStateException(e);}}
 public String create(AppUser u){String payload=u.getId()+":"+u.getRole().name()+":"+(Instant.now().getEpochSecond()+expiryHours*3600);String p=Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes(StandardCharsets.UTF_8));return p+"."+sign(p);}
 public Long userId(String token){try{String[] a=token.split("\\.");if(a.length!=2||!java.security.MessageDigest.isEqual(sign(a[0]).getBytes(StandardCharsets.UTF_8),a[1].getBytes(StandardCharsets.UTF_8)))return null;String[] p=new String(Base64.getUrlDecoder().decode(a[0]),StandardCharsets.UTF_8).split(":");if(Long.parseLong(p[2])<Instant.now().getEpochSecond())return null;return Long.parseLong(p[0]);}catch(Exception e){return null;}}
}
