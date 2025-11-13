package com.example.demo;

import com.example.employee.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.Set;

public class DemoApplication {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        Set<Employee> set = new HashSet<>();
        Employee emp = new Employee("Alice", "Dev");
        set.add(emp);

        System.out.println("Avant save - ID: " + emp.getId());
        System.out.println("HashCode avant save: " + emp.hashCode());

        em.getTransaction().begin();
        em.persist(emp);
        em.getTransaction().commit();

        System.out.println("Après save - ID: " + emp.getId());
        System.out.println("HashCode après save: " + emp.hashCode());
        System.out.println("Set contient l'employé: " + set.contains(emp));

        em.close();
        emf.close();
    }
}
