package com.project.sooktoring.user.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.common.exception.ErrorCode;
import com.project.sooktoring.common.exception.ErrorResponse;
import com.project.sooktoring.user.auth.dto.response.TokenResponse;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.project.sooktoring.common.exception.ErrorCode.*;

@Slf4j
@Component
public class AuthExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            if (e.getErrorCode() == INVALID_ACCESS_TOKEN) {
                ObjectMapper mapper = new ObjectMapper();
                Object requestBody = mapper.readValue(request.getInputStream(), Object.class); //요청 body json

                TokenResponse tokenResponse = TokenResponse.builder()
                        .requestMethod(request.getMethod())
                        .requestURI(request.getRequestURI())
                        .requestBody(requestBody)
                        .build();

                HttpSession session = request.getSession(); //세션 생성
                session.setAttribute("tokenResponse", tokenResponse); //세션에 저장
            }
            sendErrorResponse(response, e.getErrorCode());
        } catch (JwtException e) {
            log.info("error = ", e);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json; charset=UTF-8");
        ErrorResponse errorResponse = ErrorResponse.create(errorCode);
        try {
            response.getWriter().write(errorResponse.convertToJson());
        } catch (IOException e) {
            log.info("error = ", e);
        }
    }
}
