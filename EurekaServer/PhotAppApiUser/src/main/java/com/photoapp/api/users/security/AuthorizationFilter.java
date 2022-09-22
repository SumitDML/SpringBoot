//package com.photoapp.api.users.security;
//
//
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//
//
//@Component
//
//public class AuthorizationFilter extends BasicAuthenticationFilter {
//    @Autowired
//    Environment environment;
//    @Autowired
//    AuthenticationManager authenticationManager;
//    public AuthorizationFilter(AuthenticationManager authenticationManager,Environment env){
//        super(authenticationManager);
//
//        this.environment = environment;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        final String authHeader = request.getHeader(environment.getProperty("auth.token.header.name"));
//        final String property = environment.getProperty("auth.token.header.name.prefix");
//        if (authHeader == null || property == null || !authHeader.startsWith(property)) {
//            chain.doFilter(request, response);
//            return;
//        }
//        final UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//
//    }
//
//    private UsernamePasswordAuthenticationToken getAuthentication(final HttpServletRequest request) {
//
//        final String authHeader = request.getHeader(environment.getProperty("auth.token.header.name"));
//        if(authHeader==null){
//            return null;
//        }
//        final String property = environment.getProperty("auth.token.header.name.prefix");
//
//
//            final String token = authHeader.replace(property, "");
//            String userId= Jwts.parser()
//                    .setSigningKey(environment.getProperty("token.secret"))
//                    .parseClaimsJws(token)
//                    .getBody()
//                    .getSubject();
//            if(userId==null){
//                return null;
//            }
//
//
//        return new UsernamePasswordAuthenticationToken(userId,null,new ArrayList<>());
//    }
//
//
//}
