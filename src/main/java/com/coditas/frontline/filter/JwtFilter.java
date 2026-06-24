package com.coditas.frontline.filter;

import com.coditas.frontline.dto.response.ApplicationResponse;
import com.coditas.frontline.dto.response.ErrorResponse;
import com.coditas.frontline.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.coditas.frontline.constants.AuthConstants.UNAUTHORIZED;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header=request.getHeader("Authorization");
        String username=null;
        String token=null;

        if(header!=null && header.startsWith("Bearer ")){
            token=header.substring(7);
            try{
                username=jwtUtil.getUsernameFromToken(token);
            }catch (JwtException e){

                response.setStatus(401);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ErrorResponse errorResponse=new ErrorResponse(UNAUTHORIZED,401);
                ApplicationResponse<List<ErrorResponse>> applicationResponse=new ApplicationResponse<>(List.of(errorResponse));
                response.getWriter().write(objectMapper.writeValueAsString(applicationResponse));
                return;
            }
        }


    }
}
