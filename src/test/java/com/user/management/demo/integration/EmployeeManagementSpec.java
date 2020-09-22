package com.user.management.demo.integration;

import com.user.management.demo.UserManagementApplication;
import com.user.management.demo.application.common.exception.ErrorType;
import com.user.management.demo.application.employee.domain.EmployeeDomainService;
import com.user.management.demo.application.employee.web.EmployeeRestController;
import com.user.management.demo.application.employee.web.dto.EmployeeDetailsDTO;
import com.user.management.demo.domain.employee.Employee;
import com.user.management.demo.domain.employee.Grade;
import com.user.management.demo.infrastructure.GlobalRestControllerAdvice;
import com.user.management.demo.integration.config.IntegrationSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(classes = {
		UserManagementApplication.class
})
@TestPropertySource(locations = "/application-test.properties")
public class EmployeeManagementSpec extends IntegrationSpecification {

	@Autowired
	private EmployeeRestController employeeRestController;

	@Autowired
	private EmployeeDomainService employeeDomainService;

	@Autowired
	private GlobalRestControllerAdvice globalRestControllerAdvice;

	private final long randomId = 129398431;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(employeeRestController)
				.setControllerAdvice(globalRestControllerAdvice)
				.setMessageConverters(mappingJackson2HttpMessageConverter)
				.alwaysDo(MockMvcResultHandlers.print())
				.build();
	}

	@Test
	public void should_create_employee() throws Exception {
		EmployeeDetailsDTO dto = EmployeeDetailsDTO
				.of("Matt", "Johnson", Grade.MID, new BigDecimal(20000));

		this.mockMvc.perform(post("/api/v1/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(buildJson(dto)))
				.andExpect(status()
						.isOk())
				.andExpect(jsonPath("$.firstName").value(dto.getFirstName()))
				.andExpect(jsonPath("$.surname").value(dto.getSurname()))
				.andExpect(jsonPath("$.grade").value(dto.getGrade().name()))
				.andExpect(jsonPath("$.salary").value(String.valueOf(dto.getSalary())));
	}


	@Test
	public void should_update_employee() throws Exception {
		Employee createdEmployee = employeeDomainService.createOrUpdate(Employee.of("Raymond", "Nash", Grade.SENIOR, new BigDecimal(20000)));

		EmployeeDetailsDTO dto = buildWithRandomData();

		this.mockMvc.perform(put("/api/v1/employees/{id}", createdEmployee.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(buildJson(dto)))
				.andExpect(status()
						.isOk())
				.andExpect(jsonPath("$.firstName").value(dto.getFirstName()))
				.andExpect(jsonPath("$.surname").value(dto.getSurname()))
				.andExpect(jsonPath("$.grade").value(dto.getGrade().name()))
				.andExpect(jsonPath("$.salary").value(String.valueOf(dto.getSalary())));

	}

	@Test
	public void should_not_update_employee_by_not_existing_id() throws Exception {
		EmployeeDetailsDTO dto = buildWithRandomData();

		this.mockMvc.perform(put("/api/v1/employees/{id}", randomId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(buildJson(dto)))
				.andExpect(status()
						.is4xxClientError())
				.andExpect(jsonPath("$.code").value(ErrorType.ENTITY_NOT_FOUND.name()));
	}

	@Test
	public void should_get_employee_by_id() throws Exception {
		String firstName = "Michael";
		String surname = "Jackson";
		Grade grade = Grade.SENIOR;
		BigDecimal salary = new BigDecimal(12000);

		Employee createdEmployee = employeeDomainService.createOrUpdate(Employee.of(firstName, surname, grade, salary));

		this.mockMvc.perform(get("/api/v1/employees/{id}", createdEmployee.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(firstName))
				.andExpect(jsonPath("$.surname").value(surname))
				.andExpect(jsonPath("$.grade").value(grade.name()))
				.andExpect(jsonPath("$.salary").value(String.valueOf(salary)));
	}

	@Test
	public void should_not_get_employee_by_not_existing_id() throws Exception {
		this.mockMvc.perform(get("/api/v1/employees/{id}", randomId)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status()
						.is4xxClientError())
				.andExpect(jsonPath("$.code").value(ErrorType.ENTITY_NOT_FOUND.name()));
	}

	@Test
	public void should_delete_employee_by_id() throws Exception {
		String firstName = "Matthew";
		String surname = "Hudson";
		Grade grade = Grade.JUNIOR;
		BigDecimal salary = new BigDecimal(1450);

		Employee createdEmployee = employeeDomainService.createOrUpdate(Employee.of(firstName, surname, grade, salary));

		this.mockMvc.perform(delete("/api/v1/employees/{id}", createdEmployee.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status()
						.isOk());

		this.mockMvc.perform(get("/api/v1/employees/{id}", createdEmployee.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status()
						.is4xxClientError())
				.andExpect(jsonPath("$.code").value(ErrorType.ENTITY_NOT_FOUND.name()));
	}

	@Test
	public void should_not_delete_employee_by_not_existing_id() throws Exception {
		this.mockMvc.perform(delete("/api/v1/employees/{id}", randomId)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status()
						.is4xxClientError())
				.andExpect(jsonPath("$.code").value(ErrorType.ENTITY_NOT_FOUND.name()));
	}

	@Test
	public void should_find_employees_that_matches_passed_search_params() throws Exception {
		Employee firstEmployee = employeeDomainService.createOrUpdate(Employee.of("Andrew", "Jackson", Grade.DIRECTOR, new BigDecimal(35000)));
		Employee secondEmployee = employeeDomainService.createOrUpdate(Employee.of("John", "Jackson", Grade.SENIOR, new BigDecimal(15000)));
		Employee thirdEmployee = employeeDomainService.createOrUpdate(Employee.of("Mitch", "Harrison", Grade.JUNIOR, new BigDecimal(1000)));
		Employee fourthEmployee = employeeDomainService.createOrUpdate(Employee.of("Phil", "Neville", Grade.SENIOR, new BigDecimal(9000)));


		this.mockMvc.perform(get("/api/v1/employees?surname={surname}&salaryFrom={salaryFrom}", firstEmployee.getSurname(), new BigDecimal(16000))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status()
						.isOk())
				.andExpect(jsonPath("$.totalElements").value("1"))
				.andExpect(jsonPath("$.content[0].firstName").value(firstEmployee.getFirstName()))
				.andExpect(jsonPath("$.content[0].surname").value(firstEmployee.getSurname()))
				.andExpect(jsonPath("$.content[0].grade").value(firstEmployee.getGrade().name()))
				.andExpect(jsonPath("$.content[0].salary").value(String.valueOf(firstEmployee.getSalary())));


		this.mockMvc.perform(get("/api/v1/employees?grade={grade}&salaryTo={salaryTo}", Grade.SENIOR.name(), new BigDecimal(40000))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status()
						.isOk())
				.andExpect(jsonPath("$.totalElements").value("2"))
				.andExpect(jsonPath("$.content[0].firstName").value(secondEmployee.getFirstName()))
				.andExpect(jsonPath("$.content[0].surname").value(secondEmployee.getSurname()))
				.andExpect(jsonPath("$.content[0].grade").value(secondEmployee.getGrade().name()))
				.andExpect(jsonPath("$.content[0].salary").value(String.valueOf(secondEmployee.getSalary())))
				.andExpect(jsonPath("$.content[1].firstName").value(fourthEmployee.getFirstName()))
				.andExpect(jsonPath("$.content[1].surname").value(fourthEmployee.getSurname()))
				.andExpect(jsonPath("$.content[1].grade").value(fourthEmployee.getGrade().name()))
				.andExpect(jsonPath("$.content[1].salary").value(String.valueOf(fourthEmployee.getSalary())));


		this.mockMvc.perform(get("/api/v1/employees?firstName=ch", thirdEmployee.getFirstName())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status()
						.isOk())
				.andExpect(jsonPath("$.totalElements").value("1"))
				.andExpect(jsonPath("$.content[0].firstName").value(thirdEmployee.getFirstName()))
				.andExpect(jsonPath("$.content[0].surname").value(thirdEmployee.getSurname()))
				.andExpect(jsonPath("$.content[0].grade").value(thirdEmployee.getGrade().name()))
				.andExpect(jsonPath("$.content[0].salary").value(String.valueOf(thirdEmployee.getSalary())));

	}

	private EmployeeDetailsDTO buildWithRandomData() {
		String newFirstName = "Alec";
		String newSurname = "Baldwin";
		Grade newGrade = Grade.DIRECTOR;
		BigDecimal newSalary = new BigDecimal(35000);
		return EmployeeDetailsDTO.of(newFirstName, newSurname, newGrade, newSalary);
	}
}
