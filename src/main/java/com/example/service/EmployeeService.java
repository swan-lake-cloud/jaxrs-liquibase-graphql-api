package com.example.service;

import com.example.employee.EmployeeRepository;
import com.example.model.Employee;
import com.example.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(String name, String role) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setRole(role);

        return addEmployee(employee);
    }

    public Employee addEmployee(Employee employee) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(employee);
            transaction.commit();
            return employee;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create employee", e);
        }
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(Long id, String name, String role) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Employee employee = employeeRepository.findById(id);
            if (employee == null) {
                throw new IllegalArgumentException("Employee not found with id: " + id);
            }

            employee.setName(name);
            employee.setRole(role);

            em.merge(employee);
            transaction.commit();
            return employee;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update employee", e);
        }
    }

    public boolean deleteEmployee(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Employee employee = employeeRepository.findById(id);
            if (employee == null) {
                return false;
            }
            em.remove(employee);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete employee", e);
        }
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
