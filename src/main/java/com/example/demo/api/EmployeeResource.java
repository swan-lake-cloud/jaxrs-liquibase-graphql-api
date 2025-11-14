package com.example.demo.api;

import com.example.demo.entity.Employee;
import com.example.employee.EmployeeRepository;
import com.example.employee.EmployeeService;
import com.example.util.JpaUtil;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.persistence.EntityManager;

@Path("/employees")
public class EmployeeResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEmployee(Employee employee) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EmployeeRepository employeeRepository = new EmployeeRepository(em);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        employeeService.addEmployee(employee);
        em.close();
        return Response.status(Response.Status.CREATED).entity(employee).type(MediaType.APPLICATION_JSON).build();
    }
}
