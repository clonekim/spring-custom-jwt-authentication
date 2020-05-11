package bonjour.helloworld.security;

import bonjour.helloworld.security.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    AuthenticationService authenticationService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Optional<String> token = Optional.ofNullable(request.getHeader("Authorization"))
                .map(i -> {
                    if(i.startsWith("Bearer ")) {
                        return i.substring(7);
                    }
                    return null;

                });

        if(token.isPresent()) {
            log.debug("Token ==> {}", token.get());
            try {
                Optional.ofNullable(authenticationService.getAuthentication(token.get()))
                        .ifPresent(authentication -> {

                            ((UsernamePasswordAuthenticationToken) authentication).setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        });
            }catch (Exception e){
               // e.printStackTrace();
                SecurityContextHolder.clearContext();
            }

        }


        filterChain.doFilter(request, response);

    }
}
