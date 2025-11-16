package com.example.filter;

import com.example.util.JwtUtil;
import com.example.context.GraphQLContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");

        GraphQLContext context;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String username = JwtUtil.validateToken(token);
                context = GraphQLContext.authenticated(username);
            } catch (Exception e) {
                context = GraphQLContext.unauthenticated();
            }
        } else {
            context = GraphQLContext.unauthenticated();
        }

        request.setAttribute("graphql-context", context);
        chain.doFilter(request, response);
    }
}