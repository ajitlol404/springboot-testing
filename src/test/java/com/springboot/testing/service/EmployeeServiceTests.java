package com.springboot.testing.service;

import com.springboot.testing.entity.Employee;
import com.springboot.testing.exception.ResourceNotFoundException;
import com.springboot.testing.repository.EmployeeRepository;
import com.springboot.testing.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    public EmployeeRepository employeeRepository;

    @InjectMocks
    public EmployeeServiceImpl employeeServiceImpl;

    private Employee employee;

    @BeforeEach
    public void setup()
    {
//        employeeRepository= Mockito.mock(EmployeeRepository.class);
//        employeeService=new EmployeeServiceImpl(employeeRepository);
         employee= Employee.builder()
                .id(1L)
                .firstName("Ajit")
                .lastName("Kumar")
                .email("ajit@mail.com")
                .build();
    }

    //JUnit test for save employee method
        @Test
        public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject()
        {
            //given- precondition or setup

            BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
            BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

            //when- action or the behaviour we're testing
            Employee savedEmployee = employeeServiceImpl.savedEmployee(employee);

            //then- verify the output
            assertThat(savedEmployee).isNotNull();
        }

    //JUnit test for save employee method which throws exception
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenThrowsException()
    {
        //given- precondition or setup

        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        //when- action or the behaviour we're testing
        Assertions.assertThrows(ResourceNotFoundException.class,()->{
            employeeServiceImpl.savedEmployee(employee);
        });

        //then- verify the output
        Mockito.verify(employeeRepository,Mockito.never()).save(ArgumentMatchers.any(Employee.class));
    }

    //JUnit test for get all employees method
    @DisplayName("JUnit test for getAllEmployee")
        @Test
        public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList()
        {
            //given- precondition or setup
            Employee employee1= Employee.builder()
                    .id(2L)
                    .firstName("Tarun")
                    .lastName("Kumar")
                    .email("tarun@mail.com")
                    .build();
            BDDMockito.given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));

            //when- action or the behaviour we're testing
            List<Employee> employeeList = employeeServiceImpl.getAllEmployees();

            //then- verify the output
            assertThat(employeeList.size()).isNotNull();
            assertThat(employeeList.size()).isEqualTo(2);
        }


    //JUnit test for get all employees method
    @DisplayName("JUnit test for getAllEmployee (negative scenario)")
    @Test
    public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeeList()
    {
        //given- precondition or setup
        Employee employee1= Employee.builder()
                .id(2L)
                .firstName("Tarun")
                .lastName("Kumar")
                .email("tarun@mail.com")
                .build();
        BDDMockito.given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when- action or the behaviour we're testing
        List<Employee> employeeList = employeeServiceImpl.getAllEmployees();

        //then- verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    //JUnit test for
        @Test
        public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject()
        {
            //given- precondition or setup
            BDDMockito.given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

            //when- action or the behaviour we're testing
            Employee savedEmployee = employeeServiceImpl.getEmployeeById(this.employee.getId()).get();

            //then- verify the output
            assertThat(savedEmployee).isNotNull();
        }

    //JUnit test for
        @Test
        public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee()
        {
            //given- precondition or setup
            BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
            employee.setEmail("tarun@mail.com");
            employee.setFirstName("tarun");

            //when- action or the behaviour we're testing
            Employee updateEmployee = employeeServiceImpl.updateEmployee(this.employee);

            //then- verify the output
            assertThat(updateEmployee.getEmail()).isEqualTo("tarun@mail.com");
            assertThat(updateEmployee.getFirstName()).isEqualTo("tarun");
        }
}
