package com.example.graphql.datafetcher;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;

public class EmployeeQueryDataFetcher implements DataFetcher<Object> {

    private final EmployeeService employeeService;

    public EmployeeQueryDataFetcher(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Object get(DataFetchingEnvironment environment) {
        String fieldName = environment.getField().getName();

        switch (fieldName) {
            case "employee":
                return getEmployeeById(environment);
            case "employees":
                return getAllEmployees();
            default:
                return null;
        }
    }

    private Employee getEmployeeById(DataFetchingEnvironment environment) {
        Long id = Long.valueOf(environment.getArgument("id"));
        return employeeService.getEmployeeById(id);
    }

    private List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
}
