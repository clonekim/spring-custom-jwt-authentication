package bonjour.helloworld.security;

import bonjour.helloworld.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class LoginFilter extends AbstractAuthenticationProcessingFilter {


    public LoginFilter(String path, String method) {
        super(new AntPathRequestMatcher(path, method));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.debug("Processing Login request!");

        LoginRequest loginRequest = JsonUtils.toObject(request.getInputStream(), LoginRequest.class);

        if(loginRequest == null || !loginRequest.isValid())
            throw new BadCredentialsException("credential error");

        log.debug("Passing {}", loginRequest);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password));

    }


    static class LoginRequest {
        private String username;
        private String password;

        LoginRequest() {
        }

        public boolean isValid() {
            return StringUtils.hasText(username) && StringUtils.hasText(password);
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "LoginRequest{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }



}
