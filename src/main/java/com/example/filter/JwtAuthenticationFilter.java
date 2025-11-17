package com.example.filter;

import com.example.graphql.resolver.UserMutationResolver;
import com.example.util.JwtUtil;
import com.example.context.GraphQLContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import com.example.util.JwtResolver;

public class JwtAuthenticationFilter implements Filter {

    private final UserMutationResolver userMutationResolver = new UserMutationResolver();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = JwtResolver.resolveToken(httpRequest);

        GraphQLContext context;

        if (token != null) {
            if (userMutationResolver.isTokenBlacklisted(token)) {
                context = GraphQLContext.unauthenticated();
            } else {
                try {
                    String username = JwtUtil.validateToken(token);
                    context = GraphQLContext.authenticated(username);
                } catch (Exception e) {
                    context = GraphQLContext.unauthenticated();
                }
            }
        } else {
            context = GraphQLContext.unauthenticated();
        }

        request.setAttribute("graphql-context", context);
        chain.doFilter(request, response);
    }
}