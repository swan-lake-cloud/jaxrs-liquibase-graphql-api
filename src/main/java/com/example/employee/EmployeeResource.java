package com.example.employee;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.Set;

@Path("/employees")
public class EmployeeResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEmployee(Employee employee) {
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
        return Response.status(Response.Status.CREATED).entity(employee).build();
    }
}
