package com.project.sooktoring.auth.exception.exhandler;

import com.project.sooktoring.auth.dto.response.AuthExResponse;
import com.project.sooktoring.auth.exception.ExpiredAccessTokenException;
import com.project.sooktoring.auth.exception.ExpiredRefreshTokenException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredAccessTokenException e) {
            log.error(e.getMessage());
            setErrorResponse(HttpStatus.BAD_REQUEST, response, e, "/auth/refresh");
        } catch (ExpiredRefreshTokenException e) {
            log.error(e.getMessage());
            setErrorResponse(HttpStatus.BAD_REQUEST, response, e, "/auth/login");
        } catch (JwtException e) {
            log.error(e.getMessage());
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e, "/auth/login");
        }
    }

    private void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable e, String redirectUri) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");

        AuthExResponse authExResponse = AuthExResponse.builder()
                .status(status.value())
                .message(e.getMessage())
                .redirectPath(redirectUri)
                .build();
        try {
            response.getWriter().write(authExResponse.convertToJson());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
