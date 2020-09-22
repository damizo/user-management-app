package com.user.management.demo.domain.employee;

import com.user.management.demo.domain.common.TimeAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "employees")
public class Employee extends TimeAuditableEntity {

	@Column(name = "first_name")
	protected String firstName;

	@Column(name = "surname")
	protected String surname;

	@Column(name = "grade")
	@Enumerated(EnumType.STRING)
	private Grade grade;

	@Column(name = "salary")
	private BigDecimal salary;

}
