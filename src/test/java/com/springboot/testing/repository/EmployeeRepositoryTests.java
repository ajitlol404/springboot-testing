package com.springboot.testing.repository;

import com.springboot.testing.entity.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

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

        //JUnit test for get employee by id
            @Test
            public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject()
            {
                //given- precondition or setup
                Employee employee=Employee.builder()
                        .firstName("Ajit")
                        .lastName("Kumar")
                        .email("ajit@mail.com")
                        .build();

                employeeRepository.save(employee);

                //when- action or the behaviour we're testing
                Employee employeeDB = employeeRepository.findById(employee.getId()).get();

                //then- verify the output
                assertThat(employeeDB).isNotNull();
            }
        //JUnit test for get employee by email operation
        @DisplayName("JUnit test for get employee by email operation")
        @Test
        public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject()
        {
            //given- precondition or setup
            Employee employee=Employee.builder()
                    .firstName("Ajit")
                    .lastName("Kumar")
                    .email("ajit@mail.com")
                    .build();

            employeeRepository.save(employee);

            //when- action or the behaviour we're testing
            Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

            //then- verify the output
            assertThat(employeeDB).isNotNull();
        }

    //JUnit test for
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenUpdateEmployeeObject()
    {
        //given- precondition or setup
        Employee employee=Employee.builder()
                .firstName("Ajit")
                .lastName("Kumar")
                .email("ajit@mail.com")
                .build();
        employeeRepository.save(employee);

        //when- action or the behaviour we're testing
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("tarun@mail.com");
        savedEmployee.setFirstName("Tarun");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        //then- verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("tarun@mail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Tarun");
    }

    //JUnit test for delete employee
        @Test
        public void givenEmployeeObject_whenDelete_thenRemoveEmployee()
        {
            //given- precondition or setup
            Employee employee=Employee.builder()
                    .firstName("Ajit")
                    .lastName("Kumar")
                    .email("ajit@mail.com")
                    .build();
            employeeRepository.save(employee);

            //when- action or the behaviour we're testing
            employeeRepository.delete(employee);
            Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

            //then- verify the output
            assertThat(employeeOptional).isEmpty();
        }

        //JUnit test for custom query using jpql with index
            @Test
            public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject()
            {
                //given- precondition or setup
                Employee employee=Employee.builder()
                        .firstName("Ajit")
                        .lastName("Kumar")
                        .email("ajit@mail.com")
                        .build();
                employeeRepository.save(employee);

                String firstName="Ajit";
                String lastName="Kumar";

                //when- action or the behaviour we're testing
                Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

                //then- verify the output
                assertThat(savedEmployee).isNotNull();
            }


    //JUnit test for custom query using jpql with named params
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParam_thenReturnEmployeeObject()
    {
        //given- precondition or setup
        Employee employee=Employee.builder()
                .firstName("Ajit")
                .lastName("Kumar")
                .email("ajit@mail.com")
                .build();
        employeeRepository.save(employee);

        String firstName="Ajit";
        String lastName="Kumar";

        //when- action or the behaviour we're testing
        Employee savedEmployee = employeeRepository.findByJPQLNamedParam(firstName, lastName);

        //then- verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //JUnit test for custom query using native sql with index param
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSqlParam_thenReturnEmployeeObject()
    {
        //given- precondition or setup
        Employee employee=Employee.builder()
                .firstName("Ajit")
                .lastName("Kumar")
                .email("ajit@mail.com")
                .build();
        employeeRepository.save(employee);

        String firstName="Ajit";
        String lastName="Kumar";

        //when- action or the behaviour we're testing
        Employee savedEmployee = employeeRepository.findByNativeSQL(firstName, lastName);

        //then- verify the output
        assertThat(savedEmployee).isNotNull();
    }


    //JUnit test for custom query using native sql with named param
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSqlNamedParam_thenReturnEmployeeObject()
    {
        //given- precondition or setup
        Employee employee=Employee.builder()
                .firstName("Ajit")
                .lastName("Kumar")
                .email("ajit@mail.com")
                .build();
        employeeRepository.save(employee);

        String firstName="Ajit";
        String lastName="Kumar";

        //when- action or the behaviour we're testing
        Employee savedEmployee = employeeRepository.findByNativeSQLNamedParam(firstName, lastName);

        //then- verify the output
        assertThat(savedEmployee).isNotNull();
    }
}
