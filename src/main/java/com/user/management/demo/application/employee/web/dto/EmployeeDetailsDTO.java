package com.user.management.demo.application.employee.web.dto;

import com.user.management.demo.domain.employee.Grade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class EmployeeDetailsDTO {
	@NonNull
	private String firstName;

	@NonNull
	private String surname;

	@NonNull
	private Grade grade;

	@NonNull
	private BigDecimal salary;
}
