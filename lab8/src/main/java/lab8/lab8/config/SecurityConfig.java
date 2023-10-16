package lab8.lab8.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lab8.lab8.Util.Jwt;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private UserDetailsService userDetailsService;
    private Jwt jwtUtil = new Jwt();

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(jwtUtil);
        return http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((matcher) -> matcher.requestMatchers("/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/book/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/book/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/book/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/book/**").hasRole("ADMIN"))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
