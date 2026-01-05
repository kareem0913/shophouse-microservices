package com.products.filter;

import com.products.error.exception.NotAuthorizedException;
import com.products.security.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AdminAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().matches(".*/admin(/.*)?")) {
            String token = request.getHeader("Authorization");

            if(token == null || !token.startsWith("Bearer ")
                    || !jwtTokenProvider.validateToken(token.substring(7))
                    || !isAdmin(jwtTokenProvider.getUserRolesFromToken(token.substring(7)))) {
                log.info("Admin Authorization Filter");
                handlerExceptionResolver.resolveException(
                        request,
                        response,
                        null,
                        new NotAuthorizedException("access denied", "access denied")
                );
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isAdmin(List<String> roles) {
        return roles.contains("ADMIN");
    }
}
