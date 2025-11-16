package com.example.context;

public class GraphQLContext {
    private final String username;
    private final boolean authenticated;

    public GraphQLContext(String username, boolean authenticated) {
        this.username = username;
        this.authenticated = authenticated;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public static GraphQLContext unauthenticated() {
        return new GraphQLContext(null, false);
    }

    public static GraphQLContext authenticated(String username) {
        return new GraphQLContext(username, true);
    }
}
