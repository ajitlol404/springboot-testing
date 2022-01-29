package com.springboot.testing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.testing.entity.Employee;
import com.springboot.testing.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    //JUnit test for create employee
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        //given- precondition or setup
        Employee employee = Employee.builder()
                .firstName("Tarun")
                .lastName("Kumar")
                .email("tarun@mail.com")
                .build();
        BDDMockito.given(employeeService.savedEmployee(ArgumentMatchers.any(Employee.class))).willAnswer((invocation) -> invocation.getArgument(0));

        //when- action or the behaviour we're testing
        ResultActions response = mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)));

        //then- verify the output
        response
                .andDo(MockMvcResultHandlers.print()) //for printing results
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    //JUnit test for get all employee
    @Test
    public void givenListOfEmployee_whenGetAllEmployee_thenReturnEmployeeList() throws Exception {
        //given- precondition or setup
        List<Employee> listOfEmployee = new ArrayList<>();
        listOfEmployee.add(Employee.builder()
                .firstName("Karna")
                .lastName("Singh")
                .email("karan@mail.com")
                .build());
        listOfEmployee.add(Employee.builder()
                .firstName("Rahul")
                .lastName("Kumar")
                .email("rahul@mail.com")
                .build());

        given(employeeService.getAllEmployees()).willReturn(listOfEmployee);

        //when- action or the behaviour we're testing
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then- verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfEmployee.size())));
    }


    //JUnit test for  get employee by id(+ve scenario)
    @Test
    public void givenEmployeeId_whenGetEmployeeId_thenReturnEmployeeObject() throws Exception {
        //given- precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Tarun")
                .lastName("Kumar")
                .email("tarun@mail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        //when- action or the behaviour we're testing
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        //then- verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    //JUnit test for  get employee by id(-ve scenario)
    @Test
    public void givenEmployeeId_whenGetEmployeeId_thenReturnEmpty() throws Exception {
        //given- precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Tarun")
                .lastName("Kumar")
                .email("tarun@mail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        //when- action or the behaviour we're testing
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        //then- verify the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    //JUnit test for update employee (+ve scenario)
        @Test
        public void givenUpdateEmployee_whenUpdateEmployee_thenrReturnUpdateEmployeeObject() throws Exception {
            //given- precondition or setup
            long employeeId = 1L;
            Employee savedEmployee = Employee.builder()
                    .firstName("Tarun")
                    .lastName("Kumar")
                    .email("tarun@mail.com")
                    .build();

            Employee updatedEmployee = Employee.builder()
                    .firstName("Ram")
                    .lastName("Ratan")
                    .email("ram@mail.com")
                    .build();

            given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
            given(employeeService.savedEmployee(ArgumentMatchers.any(Employee.class))).willAnswer((invocation) -> invocation.getArgument(0));

            //when- action or the behaviour we're testing
            ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId).contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updatedEmployee)));

            //then- verify the output
            response.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is(updatedEmployee.getFirstName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(updatedEmployee.getLastName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(updatedEmployee.getEmail())));
        }


}
