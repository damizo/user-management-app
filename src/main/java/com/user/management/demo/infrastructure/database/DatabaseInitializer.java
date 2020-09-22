package com.user.management.demo.infrastructure.database;

import com.user.management.demo.application.employee.domain.EmployeeDomainService;
import com.user.management.demo.infrastructure.Profiles;
import com.user.management.demo.domain.employee.Employee;
import com.user.management.demo.domain.employee.Grade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Profile({Profiles.INIT_DB})
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer {

	private final EmployeeDomainService employeeDomainService;

	@PostConstruct
	public void init() {
		employeeDomainService.createOrUpdate(Employee.of("Jeff", "Bezos", Grade.DIRECTOR, new BigDecimal(90000)));
		employeeDomainService.createOrUpdate(Employee.of("Jeff", "Johnson", Grade.DIRECTOR, new BigDecimal(64000)));
		employeeDomainService.createOrUpdate(Employee.of("Andrew", "Nowak", Grade.JUNIOR, new BigDecimal(11000)));
		employeeDomainService.createOrUpdate(Employee.of("Elon", "Musk", Grade.SENIOR, new BigDecimal(75000)));
		employeeDomainService.createOrUpdate(Employee.of("John", "Nowak", Grade.SENIOR, new BigDecimal(15000)));
		log.info("CREATED EMPLOYEES: {}", employeeDomainService.findAll());
	}

}
