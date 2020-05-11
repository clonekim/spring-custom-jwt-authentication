package bonjour.helloworld.security;

import bonjour.helloworld.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
public class SimpleAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {


        log.debug("Login Failure !!!");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        JsonUtils.write(response.getWriter(), new HashMap<String, String>() {{
            put("message", exception.getMessage());
        }});
    }
}
