package com.example.graphql.datafetcher;

import com.example.model.User;
import com.example.service.UserService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.mindrot.jbcrypt.BCrypt;

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
        String password = (String) input.get("passwordHash");
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        return userService.createUser(firstName, lastName, email, username, passwordHash);
    }
}
