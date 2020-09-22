package com.user.management.demo.application.employee.domain;

import com.user.management.demo.application.common.domain.AbstractTimeAuditableMapper;
import com.user.management.demo.application.employee.web.dto.EmployeeDTO;
import com.user.management.demo.application.employee.web.dto.EmployeeDetailsDTO;
import com.user.management.demo.domain.employee.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface EmployeeMapper extends AbstractTimeAuditableMapper<Employee, EmployeeDTO> {

	@Override
	Employee toDomain(EmployeeDTO dto);

	Employee toDomain(EmployeeDetailsDTO dto);

	@Override
	@Mapping(source = "firstName", target = "employeeDetails.firstName")
	@Mapping(source = "surname", target = "employeeDetails.surname")
	@Mapping(source = "grade", target = "employeeDetails.grade")
	@Mapping(source = "salary", target = "employeeDetails.salary")
	@Mapping(source = "id", target = "id")
	@Mapping(source = "creationTime", target = "creationTime")
	@Mapping(source = "modificationTime", target = "modificationTime")
	EmployeeDTO fromDomain(Employee domain);

	@Mapping(source = "dto.firstName", target = "firstName")
	@Mapping(source = "dto.surname", target = "surname")
	@Mapping(source = "dto.grade", target = "grade")
	@Mapping(source = "dto.salary", target = "salary")
	@Mapping(source = "domain.id", target = "id")
	@Mapping(source = "domain.creationTime", target = "creationTime")
	Employee merge(EmployeeDetailsDTO dto, Employee domain);

}
