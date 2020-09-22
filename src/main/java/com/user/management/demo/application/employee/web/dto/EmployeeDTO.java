package com.user.management.demo.application.employee.web.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.user.management.demo.application.common.dto.TimeAuditableDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class EmployeeDTO extends TimeAuditableDTO {

	@JsonUnwrapped
	private EmployeeDetailsDTO employeeDetails;
}
