package org.examples.mongodb.test;

import org.examples.mongodb.model.Address;
import org.examples.mongodb.model.Employee;
import org.examples.mongodb.repo.EmployeeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class MongodbTest {


    @Autowired
    EmployeeRepo employeeRepo;

    @Test
    public void testSuccessfullySavePerson() {
        Address address = new Address("j20", "esfahan", "s1", "21111");
        Employee employee = new Employee(UUID.randomUUID().toString(), "ali", "ahmadi", 40, address);
        Employee savedEmployee = employeeRepo.save(employee);
        System.out.println("uid >>" + savedEmployee.id());
        Assertions.assertNotNull(savedEmployee);
        Assertions.assertTrue(employeeRepo.existsById(savedEmployee.id()));
    }

}
