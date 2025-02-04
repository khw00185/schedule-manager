package com.example.schedulemanager.user.jwt;

import com.example.schedulemanager.user.dto.CustomUserDetails;
import com.example.schedulemanager.user.dto.LoginRequestDto;
import com.example.schedulemanager.user.exception.RequestBodyReadException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

/*
filter를 사용하지 않으면
@PostMapping("/api/protected")
public ResponseEntity<String> protectedEndpoint(@RequestHeader("Authorization") String token) {
    if (!jwtTokenProvider.validateToken(token)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
    return ResponseEntity.ok("Access granted!");
}
컨트롤러에서 매번 이렇게 jwtTokenProvider.validateToken(token)을 검증해야 함.

고로 아래는 자동 인증처리를 위한 로직임.
 */

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;


    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/users/login");

        System.out.println("✅ LoginFilter 생성됨!");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequestDto loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);


            String userId = loginRequest.getId();
            String password = loginRequest.getPassword();
            System.out.println("Login attempt: " + userId);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, password);
            Authentication authentication = authenticationManager.authenticate(authToken);

            System.out.println("Authentication successful: " + authentication.isAuthenticated());
            return authentication;
        }catch (IOException e){
            throw new RequestBodyReadException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)throws IOException, ServletException {
        System.out.println("Login successful, generating JWT...");
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String id = userDetails.getUsername(); //getUsername()이지만 id를 반환하는 메서드임 ㅎㅎ..

        String token = jwtUtil.createJwt(id, 60 * 60 * 2 * 1000L);

        response.addHeader("Authorization", "Bearer " + token);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 응답 메시지 객체를 JSON 형식으로 변환
        String responseMessage = "{ \"message\": \"로그인이 완료되었습니다.\" }";
        response.getWriter().write(responseMessage);
        //chain.doFilter(request, response); 컨트롤러까지 로그인 요청이 안닿고 있음. JWTFilter로 넘어가면서 403 forbidden이 자꾸 일어남.. 왜지 핵폭탄 부수고 싶다.

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        String errorMessage = "Invalid username or password";

        // 응답 상태 코드 설정 (401 Unauthorized)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(errorMessage);
    }

}
