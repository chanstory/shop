package com.myapp.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.myapp.advice.exception.TokenExpiredException;

import lombok.RequiredArgsConstructor;

/**
 * JWT 토큰 검증 필터
 * 
 * @author chans
 */

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
	 
    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate<String, String> redisTemplate;
    
    //Request로 넘어오는 Jwt Token의 유효성을 검증하는 filter를 filterChain에 등록
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {  
    	HttpServletRequest httpRequest= (HttpServletRequest) request;
    	System.out.println(httpRequest.getRequestURI());
    	
        String accessToken = jwtTokenProvider.resolveAccessToken(httpRequest);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(httpRequest);
        
        System.out.println(accessToken);
        System.out.println(refreshToken);
        
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        System.out.println("test");
        //토큰 유효성 체크
        if(accessToken != null && jwtTokenProvider.validateToken(accessToken, "access")) {
        	 System.out.println("test1");
        	//로그아웃으로 만료된 토큰일경우 
        	if(accessToken.equals(vop.get("access-" + jwtTokenProvider.getUserPk(accessToken, "access")))) {
        		throw new TokenExpiredException();
        	}
        	
            Authentication auth = jwtTokenProvider.getAuthentication(accessToken, "access");
            SecurityContextHolder.getContext().setAuthentication(auth);
            
        }else if(refreshToken != null && jwtTokenProvider.validateToken(refreshToken, "refresh")) {
        	 System.out.println("test2");
        	//로그아웃으로 만료된 토큰일경우
			if(refreshToken.equals(vop.get("refresh-" + jwtTokenProvider.getUserPk(refreshToken, "refresh")))) {
				throw new TokenExpiredException();
			}
			
        	Authentication auth = jwtTokenProvider.getAuthentication(refreshToken, "refresh");
            SecurityContextHolder.getContext().setAuthentication(auth);
            
        }
        
        System.out.println("test3");
        filterChain.doFilter(request, response);
    }
}