package com.example.graphql.datafetcher;

import com.example.dto.CreateUserResponse;
import com.example.service.UserService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Map;

public class UserMutationDataFetcher implements DataFetcher<CreateUserResponse> {

    private final UserService userService;

    public UserMutationDataFetcher(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CreateUserResponse get(DataFetchingEnvironment environment) {
        Map<String, Object> input = environment.getArgument("input");
        String firstName = (String) input.get("firstName");
        String lastName = (String) input.get("lastName");
        String email = (String) input.get("email");
        String username = (String) input.get("username");
        String passwordHash = BCrypt.hashpw((String) input.get("password"), BCrypt.gensalt());
        return userService.createUser(firstName, lastName, email, username, passwordHash);
    }
}
