package bonjour.helloworld.security;

import bonjour.helloworld.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Autowired
    AuthenticationService authenticationService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.debug("Login Success !!! => {}", authentication);
        response.setStatus(HttpStatus.OK.value());
        JsonUtils.write(response.getWriter(), new HashMap<String, String>() {{
            put("token",  authenticationService.createToken(
                    authentication.getName()
            ));
        }});
    }
}
