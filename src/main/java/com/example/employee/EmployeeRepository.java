package com.example.employee;

import com.example.model.Employee;

import javax.persistence.EntityManager;
import java.util.List;

public class EmployeeRepository {

    private final EntityManager entityManager;

    public EmployeeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public List<Employee> findByName(String name) {
        return entityManager.createQuery(
                        "SELECT e FROM Employee e WHERE e.name = :name", Employee.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Employee> findByRole(String role) {
        return entityManager.createQuery(
                        "SELECT e FROM Employee e WHERE e.role = :role", Employee.class)
                .setParameter("role", role)
                .getResultList();
    }

    public void save(Employee employee) {
        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
    }

    public Employee findById(Long id) {
        return entityManager.find(Employee.class, id);
    }

    public List<Employee> findAll() {
        return entityManager.createQuery("SELECT e FROM Employee e", Employee.class)
                .getResultList();
    }
}
