package bonjour.helloworld;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HelloController {


    @GetMapping("/api/hello")
    public ResponseEntity<?> hello(Authentication authentication) {

        return ResponseEntity.ok(
                new HashMap<String, Object>() {{
                    put("currentUser", String.format("Hello!! %s", authentication.getName()));
                }}
        );
    }
}
