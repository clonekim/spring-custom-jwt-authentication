package bonjour.helloworld;

import bonjour.helloworld.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationProperties authenticationProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new SimpleUnauthorizedHandler())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/**/favicon.ico",
                        "/error",
                        authenticationProperties.loginUrl()).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // It prevents spring boot auto-configuration
        // ex: UserDetailsServiceAutoConfiguration
        return super.authenticationManagerBean();
    }


    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter( authenticationProperties.loginUrl(), authenticationProperties.loginMethod());
        loginFilter.setAuthenticationSuccessHandler(simpleAuthenticationSuccessHandler());
//        loginFilter.setAuthenticationFailureHandler(new SimpleAuthenticationFailureHandler());
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        return loginFilter;
    }

    @Bean
    public JwtAuthenticationFilter authenticationFilter() {
        return new JwtAuthenticationFilter();
    }


    @Bean
    public SimpleAuthenticationSuccessHandler simpleAuthenticationSuccessHandler() {
        return new SimpleAuthenticationSuccessHandler();
    }
}
