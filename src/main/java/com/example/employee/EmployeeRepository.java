package com.example.employee;

import com.example.model.Employee;
import com.example.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class EmployeeRepository {

    public List<Employee> findByName(String name) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Employee e WHERE e.name = :name", Employee.class)
                .setParameter("name", name)
                .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Employee> findByRole(String role) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Employee e WHERE e.role = :role", Employee.class)
                .setParameter("role", role)
                .getResultList();
        } finally {
            em.close();
        }
    }

    public void save(Employee employee) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(employee);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Employee findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public List<Employee> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class)
                .getResultList();
        } finally {
            em.close();
        }
    }
}