package com.user.management.demo.application.employee.domain;

import com.user.management.demo.application.common.exception.ErrorType;
import com.user.management.demo.application.common.exception.ParameterizedException;
import com.user.management.demo.application.employee.web.dto.EmployeeDetailsDTO;
import com.user.management.demo.application.employee.web.dto.EmployeeSearchParamsDTO;
import com.user.management.demo.infrastructure.annotations.ApplicationService;
import com.user.management.demo.application.employee.web.dto.EmployeeDTO;
import com.user.management.demo.domain.employee.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ApplicationService
@RequiredArgsConstructor
@Slf4j
public class EmployeeApplicationService {

	private final EmployeeDomainService employeeDomainService;
	private final EmployeeMapper employeeMapper;

	public EmployeeDTO getDetails(Long id) {
		return employeeDomainService.find(id)
				.map(employeeMapper::fromDomain)
				.orElseThrow(() -> new ParameterizedException(ErrorType.ENTITY_NOT_FOUND, "id", id));
	}

	public EmployeeDTO create(EmployeeDetailsDTO dto) {
		Employee employee = employeeMapper.toDomain(dto);
		Employee createdEmployee = employeeDomainService.createOrUpdate(employee);
		log.info("Employee has been created: {}", createdEmployee);

		return employeeMapper.fromDomain(createdEmployee);
	}

	public EmployeeDTO update(Long id, EmployeeDetailsDTO dto) {
		Employee employee = employeeDomainService.get(id);
		employee = employeeMapper.merge(dto, employee);

		Employee updatedEmployee = employeeDomainService.createOrUpdate(employee);
		log.info("Employee has been updated: {}", updatedEmployee);

		return employeeMapper.fromDomain(updatedEmployee);
	}

	public void delete(Long id) {
		Employee employee = employeeDomainService.get(id);
		employeeDomainService.delete(employee);
		log.info("Employee with id {} has been deleted", id);
	}

	public Page<EmployeeDTO> search(EmployeeSearchParamsDTO searchParams, PageRequest pageRequest) {
		EmployeeSpecification employeeSpecification = new EmployeeSpecification(searchParams);
		return employeeDomainService.findAll(employeeSpecification, pageRequest)
				.map(employeeMapper::fromDomain);
	}
}
