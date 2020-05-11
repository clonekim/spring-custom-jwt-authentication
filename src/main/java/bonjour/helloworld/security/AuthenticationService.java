package bonjour.helloworld.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@EnableConfigurationProperties(AuthenticationProperties.class)
public class AuthenticationService {


    @Autowired
    AuthenticationProperties authenticationProperties;


    public String createToken(String username) {

        log.debug("To token => {}", username);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + authenticationProperties.getJwt().getTtl().toMillis()))
                .signWith(authenticationProperties.getJwt().getAlgorithm(), authenticationProperties.getJwt().getSecret())
                .compact();
    }


    public Authentication getAuthentication(String token) {

        if (token == null)
            return null;

        String user = Jwts.parser()
                .setSigningKey(authenticationProperties.getJwt().getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        log.debug("To Authentication => {}", user);
        return new UsernamePasswordAuthenticationToken(
                user,
                null,
                Collections.arrayToList(new SimpleGrantedAuthority[]{ authenticationProperties.getAuthority() }));

    }

}

