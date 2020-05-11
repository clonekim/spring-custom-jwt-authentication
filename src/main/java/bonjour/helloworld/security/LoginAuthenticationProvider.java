package bonjour.helloworld.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.debug("Starting authentication - {}", authentication);
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        log.debug("Sending authenticate[{}:{}]", username, password);

        if (username.equals("admin") && password.equals("0987!@")) {
            return new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    new ArrayList<>());

        } else {
            throw new UsernameNotFoundException(String.format("Invalid username(%s)", username));
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
