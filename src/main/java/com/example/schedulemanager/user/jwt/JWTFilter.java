package com.example.schedulemanager.user.jwt;

import com.example.schedulemanager.user.dto.CustomUserDetails;
import com.example.schedulemanager.user.exception.ExpriredJwtToken;
import com.example.schedulemanager.user.exception.InvalidTokenFormatException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        if (requestURI.equals("/api/users/login") ||
                requestURI.equals("/api/users/register") ||
                (method.equals("GET") && requestURI.startsWith("/api/schedules"))) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization= request.getHeader("Authorization");

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            throw new InvalidTokenFormatException();
        }

        System.out.println("authorization now");
        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        //토큰 소멸 시간 검증 + 시그니처 검증도 함께 이루어짐.
        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            throw new ExpriredJwtToken();
        }

        String id = jwtUtil.getid(token);



        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(id, null);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
