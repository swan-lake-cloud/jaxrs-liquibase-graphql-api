package com.example.employee;

import com.example.demo.entity.Employee;

public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee addEmployee(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }
}
