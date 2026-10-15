package edu.smartlibrary.api;
import org.springframework.web.bind.annotation.*;import java.util.Map;
@RestController public class HealthController{
  @GetMapping("/") public Map<String,String> root(){return Map.of("status","ok","service","smart-library-api","version","1.0.0");}
  @GetMapping("/api/health") public Map<String,String> health(){return Map.of("status","ok","service","smart-library");}
}
