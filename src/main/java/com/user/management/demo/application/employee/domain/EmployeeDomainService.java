package com.user.management.demo.application.employee.domain;

import com.user.management.demo.application.common.domain.AbstractDomainService;
import com.user.management.demo.domain.employee.Employee;
import com.user.management.demo.infrastructure.annotations.DomainService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DomainService
public class EmployeeDomainService extends AbstractDomainService<Employee, Long, EmployeeRepository> {

	public EmployeeDomainService(EmployeeRepository employeeRepository) {
		super(employeeRepository);
	}

	public Page<Employee> findAll(EmployeeSpecification specification, PageRequest pageRequest) {
		return r.findAll(specification, pageRequest);
	}

	public Employee findBySurname(String surname) {
		return r.findBySurname(surname);
	}
}
