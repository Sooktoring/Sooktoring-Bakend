package com.project.sooktoring.user.auth.filter;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.common.exception.ErrorCode;
import com.project.sooktoring.common.exception.ErrorResponse;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
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
        } catch (CustomException e) {
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
