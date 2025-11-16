package com.example.graphql;

import com.example.employee.EmployeeRepository;
import com.example.graphql.datafetcher.EmployeeMutationDataFetcher;
import com.example.graphql.datafetcher.EmployeeQueryDataFetcher;
import com.example.service.EmployeeService;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.Map;

@Path("/graphql")
public class GraphQLEndpoint {
    private final GraphQL graphQL;

    public GraphQLEndpoint() {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        EmployeeService employeeService = new EmployeeService(employeeRepository); // Créer le service

        EmployeeQueryDataFetcher queryDataFetcher = new EmployeeQueryDataFetcher(employeeService);
        EmployeeMutationDataFetcher mutationDataFetcher = new EmployeeMutationDataFetcher(employeeService);

        GraphQLProvider provider = new GraphQLProvider(queryDataFetcher, mutationDataFetcher);
        this.graphQL = provider.buildGraphQL();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response executeGraphQL(Map<String, Object> request) {
        String query = (String) request.get("query");
        Map<String, Object> variables = (Map<String, Object>) request.get("variables");

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables != null ? variables : Collections.emptyMap())
                .build();

        ExecutionResult result = graphQL.execute(executionInput);
        return Response.ok(result.toSpecification()).build();
    }
}
