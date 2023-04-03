package com.abdiev.application.filter;

import com.abdiev.application.service.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")){
            try {
                SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
                emptyContext.setAuthentication(jwtService.validateToken(authorizationHeader.split(" ")[1]));
                SecurityContextHolder.setContext(emptyContext);
            }catch (RuntimeException e) {
                SecurityContextHolder.clearContext();
                logger.error("Can not authorize user {}",e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
