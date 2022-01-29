package com.springboot.testing.controller;

import com.springboot.testing.entity.Employee;
import com.springboot.testing.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee)
    {
        return employeeService.savedEmployee(employee);
    }


    @GetMapping
    public List<Employee> getAllEmployee()
    {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id)
    {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employee)
    {
        return employeeService.getEmployeeById(id)
            .map(savedEmployee -> {
                savedEmployee.setFirstName(employee.getFirstName());
                savedEmployee.setLastName(employee.getLastName());
                savedEmployee.setEmail(employee.getEmail());

                Employee updateEmployee = employeeService.updateEmployee(savedEmployee);
                return  new ResponseEntity<>(updateEmployee,HttpStatus.OK);
            })
            .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id)
    {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<String>("Employee deleted successfully.",HttpStatus.OK);
    }

}
