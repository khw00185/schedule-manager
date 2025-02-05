package com.example.schedulemanager.user.jwt;

import com.example.schedulemanager.user.dto.CustomUserDetails;
import com.example.schedulemanager.user.dto.UserLoginRequestDto;
import com.example.schedulemanager.user.exception.AttemptAuthenticationException;
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

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;


    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserLoginRequestDto loginRequest = objectMapper.readValue(request.getInputStream(), UserLoginRequestDto.class);

            String userId = loginRequest.getId();
            String password = loginRequest.getPassword();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, password);
            Authentication authentication = authenticationManager.authenticate(authToken);

            System.out.println("Authentication successful: " + authentication.isAuthenticated());
            return authentication;
        }catch (IOException e){
            throw new AttemptAuthenticationException();
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)throws IOException, ServletException {
        System.out.println("Login successful, generating JWT...");
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String id = userDetails.getUsername(); //getUsername()이지만 id를 반환하는 메서드임 ㅎㅎ..

        String token = jwtUtil.createJwt(id, 60 * 60 * 2 * 1000L);

        response.addHeader("Authorization", "Bearer " + token);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        String errorMessage = "Invalid username or password";

        // 응답 상태 코드 설정 (401 Unauthorized)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(errorMessage);
    }

}
