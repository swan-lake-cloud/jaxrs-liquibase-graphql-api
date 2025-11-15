package com.example.graphql;

import com.example.graphql.datafetcher.EmployeeMutationDataFetcher;
import com.example.graphql.datafetcher.EmployeeQueryDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class GraphQLProvider {

    private GraphQL graphQL;
    private final EmployeeQueryDataFetcher employeeQueryDataFetcher;
    private final EmployeeMutationDataFetcher employeeMutationDataFetcher;

    public GraphQLProvider(EmployeeQueryDataFetcher employeeQueryDataFetcher,
                           EmployeeMutationDataFetcher employeeMutationDataFetcher) {
        this.employeeQueryDataFetcher = employeeQueryDataFetcher;
        this.employeeMutationDataFetcher = employeeMutationDataFetcher;
        init();
    }

    private void init() {
        GraphQLSchema graphQLSchema = buildSchema();
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema() {
        TypeDefinitionRegistry typeRegistry = loadSchema();
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private TypeDefinitionRegistry loadSchema() {
        SchemaParser schemaParser = new SchemaParser();
        InputStream schemaStream = getClass().getClassLoader()
                .getResourceAsStream("graphql/employee.graphqls");
        if (schemaStream == null) {
            throw new RuntimeException("Schema file not found: graphql/employee.graphqls");
        }
        Reader schemaReader = new InputStreamReader(schemaStream);
        return schemaParser.parse(schemaReader);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder
                        .dataFetcher("employee", employeeQueryDataFetcher)
                        .dataFetcher("employees", employeeQueryDataFetcher))
                .type("Mutation", builder -> builder
                        .dataFetcher("createEmployee", employeeMutationDataFetcher)
                        .dataFetcher("updateEmployee", employeeMutationDataFetcher)
                        .dataFetcher("deleteEmployee", employeeMutationDataFetcher))
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }

    public GraphQL buildGraphQL() {
        if (graphQL == null) {
            init();
        }
        return graphQL;
    }
}
