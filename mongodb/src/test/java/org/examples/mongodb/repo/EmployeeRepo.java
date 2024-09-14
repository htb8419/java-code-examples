package org.examples.mongodb.repo;

import org.examples.mongodb.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee, String> {

}
