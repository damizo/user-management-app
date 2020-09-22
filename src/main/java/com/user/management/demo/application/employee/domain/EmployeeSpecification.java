package com.user.management.demo.application.employee.domain;

import com.google.common.base.Strings;
import com.user.management.demo.application.employee.web.dto.EmployeeSearchParamsDTO;
import com.user.management.demo.domain.employee.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class EmployeeSpecification implements Specification<Employee> {

	private final EmployeeSearchParamsDTO searchParams;

	@Override
	public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();

		if (!Strings.isNullOrEmpty(searchParams.getFirstName())) {
			predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + searchParams.getFirstName() + "%"));
		}

		if (!Strings.isNullOrEmpty(searchParams.getSurname())) {
			predicates.add(criteriaBuilder.like(root.get("surname"), "%" + searchParams.getSurname() + "%"));
		}

		if (Objects.nonNull(searchParams.getGrade())) {
			predicates.add(criteriaBuilder.equal(root.get("grade"), searchParams.getGrade()));
		}

		if (Objects.nonNull(searchParams.getSalaryFrom())) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), searchParams.getSalaryFrom()));
		}

		if (Objects.nonNull(searchParams.getSalaryTo())) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("salary"), searchParams.getSalaryTo()));
		}

		return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
	}
}
