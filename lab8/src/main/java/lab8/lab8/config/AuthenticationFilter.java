package lab8.lab8.config;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab8.lab8.Util.Jwt;
import lab8.lab8.model.Role;

public class AuthenticationFilter extends OncePerRequestFilter {

    private final Jwt jwtUtil;

    public AuthenticationFilter(Jwt jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse responce, FilterChain filterchain)
            throws ServletException, IOException {
        try {
            final String header = request.getHeader("Authorization");

            if (header == null || !header.startsWith("Beearer ")) {
                filterchain.doFilter(request, responce);
                return;
            }

            final String token = header.split(" ")[1].trim();

            if (!jwtUtil.validateToken(token)) {
                filterchain.doFilter(request, responce);
                return;
            }

            String username = jwtUtil.getUsernameFromToken(token);
            Role role = Role.valueOf(jwtUtil.getRoleFromToken(token));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null,
                    java.util.Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toString())));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterchain.doFilter(request, responce);
        } catch (java.io.IOException | ServletException e) {
            e.printStackTrace();
        }
    }

}
