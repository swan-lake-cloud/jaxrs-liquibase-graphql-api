package com.example.graphql.datafetcher;

import com.example.model.User;
import com.example.service.UserService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Map;

public class UserMutationDataFetcher implements DataFetcher<User> {

    private final UserService userService;

    public UserMutationDataFetcher(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User get(DataFetchingEnvironment environment) {
        Map<String, Object> input = environment.getArgument("input");
        String firstName = (String) input.get("firstName");
        String lastName = (String) input.get("lastName");
        String email = (String) input.get("email");
        String username = (String) input.get("username");
        String passwordHash = (String) input.get("passwordHash");

        return userService.createUser(firstName, lastName, email, username, passwordHash);
    }
}
