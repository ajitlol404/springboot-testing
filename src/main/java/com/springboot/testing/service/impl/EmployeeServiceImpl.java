package com.springboot.testing.service.impl;

import com.springboot.testing.entity.Employee;
import com.springboot.testing.exception.ResourceNotFoundException;
import com.springboot.testing.repository.EmployeeRepository;
import com.springboot.testing.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee savedEmployee(Employee employee) {

        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee already exists with the given email : "+employee.getEmail());
        }

        return employeeRepository.save(employee);
    }
}
