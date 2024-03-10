package com.project_service.bookingservice.security.jwt;

import com.project_service.bookingservice.security.CustomUserDetails;
import com.project_service.bookingservice.security.CustomUserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if (token != null && jwtService.validateToken(token)) {
            setCustomUserDetailsToSecurityContextHolder(token);
        }
        filterChain.doFilter(request, response);
    }

    public void setCustomUserDetailsToSecurityContextHolder(String token) {
        String email = jwtService.getEmailFromToken(token);
        CustomUserDetails customUserDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails,
                null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
