package com.example.quiz.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    public JwtFilter() {
        System.out.println("==============================JwtFilter bean created");
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        /*
         * System.out.println("Auth header====================: " + authHeader);
         * if (authHeader != null && authHeader.startsWith("Bearer ")) {
         * String token = authHeader.substring(7); // remove "Bearer "
         * 
         * if (jwtUtil.isTokenValid(token)) {
         * String email = jwtUtil.extractEmail(token);
         * 
         * UsernamePasswordAuthenticationToken auth =
         * new UsernamePasswordAuthenticationToken(email, null, List.of());
         * 
         * SecurityContextHolder.getContext().setAuthentication(auth);
         * }
         * }
         */

        System.out.println("Auth header====================: " + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // ADD THESE
            System.out.println("Token: " + token);
            System.out.println("Is valid: " + jwtUtil.isTokenValid(token));
            if (jwtUtil.isTokenValid(token)) {
                String email = jwtUtil.extractEmail(token);
                String role = jwtUtil.extractRole(token); // ← extract role from token

                List<SimpleGrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority("ROLE_" + role) // e.g. ROLE_ADMIN
                );

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null,
                        authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}