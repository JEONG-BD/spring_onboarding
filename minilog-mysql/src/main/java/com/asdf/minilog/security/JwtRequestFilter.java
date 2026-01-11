package com.asdf.minilog.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(
            JwtRequestFilter.class);

    @Autowired
    private UserDetailsService jwtUserDetailService;

    @Autowired
    private JwtUtil jwtTokenUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if(requestHeader != null && requestHeader.startsWith("Bearer ")){
            jwt = requestHeader.substring(7);
            try{
                username= jwtTokenUtil.getUsernameFromToken(jwt);
            } catch (IllegalArgumentException e){
                logger.error("Unable to get JWT", e);
            } catch (ExpiredJwtException e){
                logger.warn("JWT has expired", e);
            }
        } else {
            logger.warn("JWT does not begin with Bearer String");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication()  == null){
            UserDetails userDetails = this.jwtUserDetailService.loadUserByUsername(username);
            if(jwtTokenUtil.validateToken(jwt, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(
                        usernamePasswordAuthenticationToken);
            }else{
                logger.warn("JWT is not valid");
            }
        }
        filterChain.doFilter(request, response);
    }
}
