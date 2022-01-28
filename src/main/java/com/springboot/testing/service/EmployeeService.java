package com.springboot.testing.service;

import com.springboot.testing.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee savedEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(long id);
    Employee updateEmployee(Employee updatedEmployee);
}
