package com.springboot.testing.repository;

import com.springboot.testing.entity.Employee;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    //JUnit test for save employee
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee()
    {
        //given- precondition or setup
        Employee employee=Employee.builder()
                .firstName("Ajit")
                .lastName("Kumar")
                .email("ajit@mail.com")
                .build();

        //when- action or the behaviour we're testing
        Employee savedEmployee = employeeRepository.save(employee);

        //then- verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    //JUnit test for get all employee
        @Test
        public void givenEmployeeList_whenFindAll_thenEmployeeList()
        {
            //given- precondition or setup
            Employee employee=Employee.builder()
                    .firstName("Ajit")
                    .lastName("Kumar")
                    .email("ajit@mail.com")
                    .build();
            Employee employee1=Employee.builder()
                    .firstName("Ram")
                    .lastName("Ratan")
                    .email("ram@mail.com")
                    .build();

            List<Employee> employees=List.of(employee,employee1);
            employeeRepository.saveAll(employees);

            //when- action or the behaviour we're testing
            List<Employee> employeeList = employeeRepository.findAll();

            //then- verify the output
            assertThat(employeeList).isNotNull();
            assertThat(employeeList.size()).isEqualTo(2);
        }
}
