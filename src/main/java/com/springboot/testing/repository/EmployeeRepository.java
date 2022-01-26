package com.springboot.testing.repository;

import com.springboot.testing.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Query("select e from Employee e where e.firstName=?1 and e.lastName=?2")//For JPQL query we use class name as table.
    Employee findByJPQL(String firstName,String lastName);
}
