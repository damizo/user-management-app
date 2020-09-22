package com.user.management.demo.application.employee.web.dto;

import com.user.management.demo.domain.employee.Grade;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class EmployeeSearchParamsDTO {
	private String firstName;
	private String surname;
	private Grade grade;
	private BigDecimal salaryFrom;
	private BigDecimal salaryTo;

}
