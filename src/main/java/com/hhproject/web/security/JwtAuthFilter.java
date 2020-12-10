package com.hhproject.web.security;


import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;

    private final UserDetailsService userDetailsService;


    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);

        if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
            String username = jwtProvider.getUsernameFromJwt(jwt);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}


















































//    @SneakyThrows
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String jwt = getJwtFromRequest(request);
//        System.out.println("DZIALA DOFILTERINTERNAL");
//        if(StringUtils.hasText(jwt)){
//            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtProvider.getUsernameFromJwt(jwt));
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails
//                    ,null,userDetails.getAuthorities());
//            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            System.out.println("DZIALA STRINGUTILS IF");
//            System.out.println(jwt);
//
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        }
//        filterChain.doFilter(request,response);
//
//
//    }
//
//    private String getJwtFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//
//        if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        System.out.println("BEARER TOKEN: "+ bearerToken);
//        return bearerToken;
//
//    }

