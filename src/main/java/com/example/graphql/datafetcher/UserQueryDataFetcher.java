package com.example.graphql.datafetcher;

import com.example.model.User;
import com.example.service.UserService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;

public class UserQueryDataFetcher implements DataFetcher<Object> {

    private final UserService userService;

    public UserQueryDataFetcher(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Object get(DataFetchingEnvironment environment) {
        String fieldName = environment.getField().getName();

        switch (fieldName) {
            case "user":
                return getUserById(environment);
            case "users":
                return getAllUsers();
            default:
                return null;
        }
    }

    private User getUserById(DataFetchingEnvironment environment) {
        Long id = Long.valueOf(environment.getArgument("id"));
        return userService.getUserById(id);
    }

    private List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
