package com.springboot.testing.service;

import com.springboot.testing.entity.Employee;
import com.springboot.testing.exception.ResourceNotFoundException;
import com.springboot.testing.repository.EmployeeRepository;
import com.springboot.testing.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

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
}
