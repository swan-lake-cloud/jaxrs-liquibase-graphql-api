package com.example.graphql.datafetcher;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Map;

public class EmployeeMutationDataFetcher implements DataFetcher<Employee> {

    private final EmployeeService employeeService;

    public EmployeeMutationDataFetcher(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Employee get(DataFetchingEnvironment environment) {
        Map<String, Object> input = environment.getArgument("input");
        String name = (String) input.get("name");
        String role = (String) input.get("role");

        return employeeService.createEmployee(name, role);
    }
}
