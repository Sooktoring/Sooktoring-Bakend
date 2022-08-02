package com.project.sooktoring.user.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sooktoring.exception.handler.dto.AuthExResponse;
import com.project.sooktoring.user.auth.dto.response.TokenResponse;
import com.project.sooktoring.exception.domain.user.auth.ExpiredAccessTokenException;
import com.project.sooktoring.exception.domain.user.auth.ExpiredRefreshTokenException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Component
public class AuthExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredAccessTokenException e) {
            ObjectMapper mapper = new ObjectMapper();
            Object requestBody = mapper.readValue(request.getInputStream(), Object.class); //요청 body json

            TokenResponse tokenResponse = TokenResponse.builder()
                    .requestMethod(request.getMethod())
                    .requestURI(request.getRequestURI())
                    .requestBody(requestBody)
                    .build();

            HttpSession session = request.getSession(); //세션 생성
            session.setAttribute("tokenResponse", tokenResponse); //세션에 저장

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
        response.setContentType("application/json; charset=UTF-8");

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
